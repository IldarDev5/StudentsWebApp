package ru.ildar.controllers.admin.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.LocalizedCityPojo;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.services.CityService;
import ru.ildar.services.LanguageService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/cities/localize")
public class CitiesLocalizationController
{
    @Autowired
    private CityService cityService;
    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView localizeCity(@RequestParam("cityId") int cityId, ModelMap model)
    {
        model.addAttribute("languages", languageService.getAllLanguages());
        model.addAttribute("city", cityService.getById(cityId));
        return new ModelAndView("localizeCity", "cityLocalization", new LocalizedCityPojo());
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getLocalization(@RequestParam("cityId") int cityId,
                                               @RequestParam("language") String langAbbrev)
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
            model.addAttribute("languages", languageService.getAllLanguages());
            model.addAttribute("city", cityService.getById(cityLocPojo.getCityId()));
            return new ModelAndView("localizeCity", "cityLocalization", cityLocPojo);
        }

        LocalizedCity cityLoc = new LocalizedCity(cityLocPojo.getLocCityId(), cityLocPojo.getCityTranslation());
        cityLoc.setTranslatedName(new String(cityLoc.getTranslatedName().getBytes("ISO-8859-1"), "UTF-8"));
        cityService.setCityAndLanguageAndAddCityLocalization(cityLocPojo.getCityId(),
                cityLocPojo.getLanguage(), cityLoc);
        return new ModelAndView("redirect:/admin/cities");
    }
}
