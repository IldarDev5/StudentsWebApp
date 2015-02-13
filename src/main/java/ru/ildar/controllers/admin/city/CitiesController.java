package ru.ildar.controllers.admin.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.services.CityService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Controller that allows performing CRUD operations on cities
 */
@Controller
@RequestMapping(value = "/admin/cities")
public class CitiesController
{
    private CityService cityService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        cityService = serviceFactory.getCityService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView viewCities()
    {
        return new ModelAndView("admin/city/cities", "cities", cityService.getAllCities());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> addCity(@RequestBody City city)
    {
        Map<String, String> result = new HashMap<>();
        cityService.addCity(city);

        //Adding default localization - English
        LocalizedCity localizedCity = new LocalizedCity(city.getId(), city.getCityName());
        cityService.setCityAndLanguageAndAddCityLocalization(city.getId(), Locale.US.getLanguage(), localizedCity);

        result.put("cityId", String.valueOf(city.getId()));
        result.put("status", "OK");
        return result;
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public ModelAndView removeCity(@RequestParam("cityId") int cityId)
    {
        cityService.removeCity(cityId);
        return new ModelAndView("redirect:/admin/cities");
    }
}
