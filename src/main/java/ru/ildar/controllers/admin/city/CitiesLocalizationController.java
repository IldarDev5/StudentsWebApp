package ru.ildar.controllers.admin.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.LocalizedCityPojo;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.services.CityService;
import ru.ildar.services.LanguageService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/cities/localize")
public class CitiesLocalizationController
{
    private CityService cityService;
    private LanguageService languageService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        cityService = serviceFactory.getCityService();
        languageService = serviceFactory.getLanguageService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView localizeCity(@RequestParam("cityId") int cityId, ModelMap model)
    {
        return cityLocalizationView(new LocalizedCityPojo(), cityId, model);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getLocalization(@RequestParam("cityId") int cityId,
                                               @RequestParam("language") String langAbbrev)
            //Get localization of the specified city in the specified language via AJAX call
    {
        Map<String, String> result = new HashMap<>();
        LocalizedCity cityLoc = cityService.getLocalization(cityId, langAbbrev);

        if(cityLoc != null)
        {
            result.put("locCityId", String.valueOf(cityLoc.getId()));
            result.put("translation", cityLoc.getTranslatedName());
        }
        return result;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView localizeCity(@ModelAttribute("cityLocalization") @Valid LocalizedCityPojo
                                             cityLocPojo, BindingResult result, ModelMap model)
            throws UnsupportedEncodingException
    {
        if(result.hasErrors())
        {
            return cityLocalizationView(cityLocPojo, cityLocPojo.getCityId(), model);
        }

        LocalizedCity cityLoc = new
                LocalizedCity(cityLocPojo.getLocCityId(), cityLocPojo.getCityTranslation());
        if(cityLoc.getTranslatedName().length() == 0)
            //Admin tries to remove the city localization;
            //Remove localization only if it's not the default localization(English)
        {
            if(!cityService.removeCityLocalization(cityLocPojo.getLocCityId()))
            {
                //Admin tried to remove the default localization
                result.rejectValue("cityTranslation", "localizeCity.cantDeleteDefaultTranslation");
                return cityLocalizationView(cityLocPojo, cityLocPojo.getCityId(), model);
            }
            return new ModelAndView("redirect:/admin/cities");
        }

        cityLoc.setTranslatedName(new String(cityLoc.getTranslatedName().getBytes("ISO-8859-1"), "UTF-8"));
        cityService.setCityAndLanguageAndAddCityLocalization(cityLocPojo.getCityId(), cityLocPojo.getLanguage(), cityLoc);
        return new ModelAndView("redirect:/admin/cities");
    }

    private ModelAndView cityLocalizationView(LocalizedCityPojo cityLocPojo, int cityId, ModelMap model)
    {
        model.addAttribute("languages", languageService.getAllLanguages());
        model.addAttribute("city", cityService.getById(cityId));
        return new ModelAndView("localizeCity", "cityLocalization", cityLocPojo);
    }
}
