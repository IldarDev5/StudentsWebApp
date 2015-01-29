package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.UniversityInfoPojo;
import ru.ildar.database.entities.University;
import ru.ildar.database.entities.UniversityDescription;
import ru.ildar.services.FacultyService;
import ru.ildar.services.UniversityService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;

/**
 * Controller that handles showing information to guests
 */
@Controller
public class GuestController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private FacultyService facultyService;

    @RequestMapping(value = "/startPage", method = RequestMethod.GET)
    public String startPage()
    {
        return "startPage";
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage()
    {
        return "loginPage";
    }

    @RequestMapping(value = "/unis/info", method = RequestMethod.GET)
    public ModelAndView viewUniversityInfo()
    {
        return new ModelAndView("unisInfo", "university", new UniversityInfoPojo());
    }

    @RequestMapping(value = "/unis/info", method = RequestMethod.POST)
    public ModelAndView viewUniversityInfo(@ModelAttribute @Valid UniversityInfoPojo pojo, Locale locale,
                                           BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            return new ModelAndView("unisInfo", "university", pojo);
        }

        UniversityDescription description = universityService.
                getDescriptionByLanguageAbbrev(pojo.getUnId(), locale.getLanguage());
        if(description == null)
            //No description for the user locale language;
            //Try English; if there's no English, try first found;
            //If there are no descriptions for this university at all,
            //return information about this to user
        {
            description = universityService
                    .getDescriptionByLanguageAbbrev(pojo.getUnId(), Locale.US.getLanguage());
            if(description == null)
                //No English, try to find any
            {
                description = universityService.getFirstDescriptionForUniversity(pojo.getUnId());
                if(description == null)
                    //No descriptions at all for this university
                {
                    model.addAttribute("description",
                            new UniversityDescription(null, null, null, null, null));
                    return new ModelAndView("unisInfo", "university", pojo);
                }
            }
        }

        int studentsCount = facultyService.getStudentsCount(pojo.getUnId());

        model.addAttribute("description", description);
        model.addAttribute("studentsCount", studentsCount);
        return new ModelAndView("unisInfo", "university", pojo);
    }

    @RequestMapping(value = "/unis/image", method = RequestMethod.GET)
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
