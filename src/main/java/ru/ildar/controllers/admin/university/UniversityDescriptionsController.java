package ru.ildar.controllers.admin.university;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.University;
import ru.ildar.database.entities.UniversityDescription;
import ru.ildar.services.LanguageService;
import ru.ildar.services.UniversityService;

import java.beans.PropertyEditorSupport;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

@Controller
@RequestMapping("/admin/unis/description")
public class UniversityDescriptionsController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView viewDescription(@RequestParam("unId") int unId, Locale locale, ModelMap model)
    {
        String lang = languageService.getLanguageByAbbreviation(locale.getLanguage());
        return viewDescription(unId, lang, model);
    }

    @RequestMapping(value = "{lang}", method = RequestMethod.GET)
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

        model.addAttribute("languages", languageService.getAllLanguagesValues());
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

    @RequestMapping(value = "", method = RequestMethod.POST)
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
