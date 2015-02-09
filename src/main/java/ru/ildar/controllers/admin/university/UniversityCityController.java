package ru.ildar.controllers.admin.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.UniversityInfoPojo;
import ru.ildar.services.CityService;
import ru.ildar.services.UniversityService;

@Controller
@RequestMapping("/admin/unis/city")
public class UniversityCityController
{
    @Autowired
    private CityService cityService;
    @Autowired
    private UniversityService universityService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView setCity(@RequestParam("unId") int unId, ModelMap model)
    {
        model.addAttribute("cities", cityService.getAllCities());
        model.addAttribute("university", universityService.getById(unId));
        UniversityInfoPojo universityInfoPojo = new UniversityInfoPojo();
        universityInfoPojo.setUnId(unId);
        return new ModelAndView("setCityToUni", "uni", universityInfoPojo);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView setCity(@ModelAttribute("uni") UniversityInfoPojo uni)
    {
        universityService.setCity(uni.getUnId(), uni.getCityId());
        return new ModelAndView("redirect:/admin/unis");
    }
}
