package ru.ildar.controllers.admin.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.City;
import ru.ildar.services.CityService;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin/cities")
public class CitiesController
{
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView viewCities()
    {
        return new ModelAndView("cities", "cities", cityService.getAllCities());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, String> addCity(@RequestBody City city)
    {
        Map<String, String> result = new HashMap<>();
        cityService.addCity(city);

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
