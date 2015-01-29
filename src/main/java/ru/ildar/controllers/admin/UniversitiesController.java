package ru.ildar.controllers.admin;

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
    @Autowired
    private LanguageService languageService;

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

    @RequestMapping(value = "description", method = RequestMethod.GET)
    public ModelAndView viewDescription(@RequestParam("unId") int unId, Locale locale, ModelMap model)
    {
        String lang = languageService.getLanguageByAbbreviation(locale.getLanguage());
        return viewDescription(unId, lang, model);
    }

    @RequestMapping(value = "description/{lang}", method = RequestMethod.GET)
    public ModelAndView viewDescription(@RequestParam("unId") int unId,
                                        @PathVariable("lang") String lang,
                                        ModelMap model)
    {
        UniversityDescription descr = universityService.getDescription(unId, lang);
        if(descr == null)
        {
            University uni = universityService.getById(unId);
            descr = new UniversityDescription("", null, lang, null, uni);
        }

        model.addAttribute("languages", languageService.getAllLanguages());
        return new ModelAndView("uniDescription", "description", descr);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(University.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text) throws IllegalArgumentException
            {
                int id = Integer.parseInt(text);
                setValue(universityService.getById(id));
            }
        });
    }

    @RequestMapping(value = "description", method = RequestMethod.POST)
    public String setDescription(@ModelAttribute("description") UniversityDescription descr,
                                       Principal principal)
            throws UnsupportedEncodingException
    {
        descr.setLastChangeDate(new Timestamp(new Date().getTime()));
        try
        {
            universityService.setUniversityDescription(descr, principal.getName());
        }
        catch(JpaSystemException exc)
        {
            if(exc.getCause().getCause() instanceof ConstraintViolationException)
                //Attempt was made to insert a row in UN_DESCRIPTION the language field
                //of which was distorted due to encoding conversions
            {
                descr.setLanguage(new String(descr.getLanguage().getBytes("ISO-8859-1"), "UTF-8"));
                descr.setDescription(new String(descr.getDescription().getBytes("ISO-8859-1"), "UTF-8"));
                universityService.setUniversityDescription(descr, principal.getName());
            }
            else
                exc.printStackTrace();
        }

        return "redirect:/admin/unis";
    }
}
