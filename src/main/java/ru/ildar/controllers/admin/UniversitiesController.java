package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.UniversityPojo;
import ru.ildar.database.entities.University;
import ru.ildar.services.CityService;
import ru.ildar.services.UniversityService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/unis")
public class UniversitiesController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView getUniversities(ModelMap model)
    {
        return universities(1, model);
    }

    @RequestMapping(value = "{page_n}", method = RequestMethod.GET)
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

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addUniversity(ModelMap model)
    {
        model.addAttribute("uni", new UniversityPojo());
        model.addAttribute("cities", cityService.getAllCities());

        return new ModelAndView("addUniversity", "uni", new UniversityPojo());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addUniversity(@ModelAttribute("uni") UniversityPojo uni)
    {
        University university = new University(uni.getUnName(), uni.getUnAddress(), null, null);
        universityService.setCityAndAddUniversity(university, uni.getCitySelect());

        return new ModelAndView("redirect:/admin/unis");
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public ModelAndView removeUniversity(@RequestParam("unId") int unId)
    {
        universityService.removeUniversity(unId);
        return new ModelAndView("redirect:/admin/unis");
    }

    @RequestMapping(value = "image", method = RequestMethod.GET)
    public void universityImage(@RequestParam("unId") int unId, HttpServletResponse response)
            throws IOException
    {
        University university = universityService.getById(unId);
        byte[] image = university.getUnImage();

        if(image != null)
        {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(image);
        }
        else
            response.sendRedirect("/images/no_uni_image.png");

        response.getOutputStream().close();
    }
}
