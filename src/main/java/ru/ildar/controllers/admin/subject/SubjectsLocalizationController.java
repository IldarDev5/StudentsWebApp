package ru.ildar.controllers.admin.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.LocalizedSubjectPojo;
import ru.ildar.database.entities.LocalizedSubject;
import ru.ildar.services.LanguageService;
import ru.ildar.services.SubjectService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller that handles subjects localization
 */
@Controller
@RequestMapping("/admin/subjects/localized")
public class SubjectsLocalizationController
{
    private SubjectService subjectService;
    private LanguageService languageService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        subjectService = serviceFactory.getSubjectService();
        languageService = serviceFactory.getLanguageService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView localizedSubjects(@RequestParam("subject") String subject, ModelMap model)
    {
        LocalizedSubjectPojo subjectPojo = new LocalizedSubjectPojo();
        subjectPojo.setSubjectName(subject);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("languages", languageService.getAllLanguages());
        return new ModelAndView("admin/subject/localizedSubjects", "subject", subjectPojo);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView localizedSubjects(@ModelAttribute("subject") @Valid LocalizedSubjectPojo
                                                 locSubjectPojo, BindingResult result, ModelMap model)
            throws UnsupportedEncodingException
    {
        if(result.hasErrors())
        {
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("languages", languageService.getAllLanguages());
            return new ModelAndView("admin/subject/localizedSubjects", "subject", locSubjectPojo);
        }

        if(locSubjectPojo.getSubjectTranslation().length() == 0 && locSubjectPojo.getId() != null)
            //Admin tries to set the subject translation empty
        {
            subjectService.removeSubjectLocalization(locSubjectPojo.getId());
            return new ModelAndView("redirect:/admin/subjects");
        }

        //transform subject translation encoding from ISO-8859-1 to UTF-8
        //otherwise text will not be readable
        locSubjectPojo.setSubjectTranslation(new String(locSubjectPojo
                .getSubjectTranslation().getBytes("ISO-8859-1"), "UTF-8"));
        LocalizedSubject subject = new LocalizedSubject(locSubjectPojo.getId(),
                null, locSubjectPojo.getSubjectTranslation(), null);
        subjectService.setSubjectAndLangAndSaveLocalization
                (locSubjectPojo.getSubjectName(), locSubjectPojo.getLanguageAbbrev(), subject);
        return new ModelAndView("redirect:/admin/subjects");
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getLocalization(@RequestParam("name") String subjectName,
                                  @RequestParam("lang") String languageAbbrev)
    {
        Map<String, String> result = new HashMap<>();
        LocalizedSubject locSubject = subjectService.getSubjectLocalization(subjectName, languageAbbrev);
        if(locSubject != null)
        {
            result.put("translation", locSubject.getSubjectTranslation());
            result.put("id", String.valueOf(locSubject.getId()));
        }

        return result;
    }
}
