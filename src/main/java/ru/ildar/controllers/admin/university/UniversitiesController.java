package ru.ildar.controllers.admin.university;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.UniversityPojo;
import ru.ildar.database.entities.University;
import ru.ildar.database.entities.UniversityDescription;
import ru.ildar.services.CityService;
import ru.ildar.services.LanguageService;
import ru.ildar.services.UniversityService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Administrator controller that handles CRUD operations with universities
 * and universities descriptions
 */
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
        model.addAttribute("cities", cityService.getAllCities());
        return new ModelAndView("addUniversity", "uni", new UniversityPojo());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addUniversity(@ModelAttribute("uni") @Valid UniversityPojo uni,
                                      BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAttribute("cities", cityService.getAllCities());
            return new ModelAndView("addUniversity", "uni", uni);
        }

        University university = new University(uni.getUnName(), uni.getUnAddress(), null, null);
        try
        {
            universityService.setCityAndAddUniversity(university, uni.getCitySelect());
        }
        catch(DuplicateKeyException exc)
                //University with such name and city already exists
        {
            model.addAttribute("cities", cityService.getAllCities());
            model.addAttribute("uniExists", true);
            return new ModelAndView("addUniversity", "uni", uni);
        }

        return new ModelAndView("redirect:/admin/unis");
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public ModelAndView removeUniversity(@RequestParam("unId") int unId)
    {
        universityService.removeUniversity(unId);
        return new ModelAndView("redirect:/admin/unis");
    }

    @RequestMapping(value = "image", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String setUniImage(@RequestParam("uploadUnId") int unId,
                              @RequestParam("uniPic") MultipartFile file,
                              @RequestParam("pageNumber") int pageNumber)
            throws IOException
    {
        if(!file.isEmpty())
        {
            byte[] bytes = file.getBytes();
            universityService.setImage(unId, bytes);
            return "redirect:/admin/unis/" + pageNumber;
        }

        return null;
    }
}
