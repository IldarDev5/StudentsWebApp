package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.University;
import ru.ildar.services.CityService;
import ru.ildar.services.UniversityService;

import java.util.List;

@Controller
public class UniversitiesController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "/admin/unis", method = RequestMethod.GET)
    public ModelAndView getUniversities(ModelMap model)
    {
        return universities(1, model);
    }

    @RequestMapping(value = "/admin/unis/{page_n}", method = RequestMethod.GET)
    public ModelAndView universities(@PathVariable("page_n") int pageNumber, ModelMap model)
    {
        int UNIS_PER_PAGE = 25;
        List<University> universities = universityService.getUniversities(pageNumber - 1, UNIS_PER_PAGE);
        int count = universityService.getPagesCount(UNIS_PER_PAGE);

        model.addAttribute("universities", universities);
        model.addAttribute("pagesCount", count);
        model.addAttribute("pageNumber", pageNumber);

        return new ModelAndView("unis");
    }

    @RequestMapping(value = "/admin/unis/add", method = RequestMethod.GET)
    public ModelAndView addUniversity(ModelMap model)
    {
        model.addAttribute("uni", new UniversityPojo());
        model.addAttribute("cities", cityService.getAllCities());

        return new ModelAndView("addUniversity", "uni", new UniversityPojo());
    }

    @RequestMapping(value = "/admin/unis/add", method = RequestMethod.POST)
    public ModelAndView addUniversity(@ModelAttribute("uni") UniversityPojo uni)
    {
        University university = new University(uni.getUnName(), uni.getUnAddress(), null, null);
        universityService.setCityAndAddUniversity(university, uni.getCitySelect());

        return new ModelAndView("redirect:/admin/unis");
    }
}
