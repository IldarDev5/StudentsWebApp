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
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView localizedSubjects(@RequestParam("subject") String subject, ModelMap model)
    {
        LocalizedSubjectPojo subjectPojo = new LocalizedSubjectPojo();
        subjectPojo.setSubjectName(subject);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("languages", languageService.getAllLanguages());
        return new ModelAndView("localizedSubjects", "subject", subjectPojo);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ModelAndView localizedSubjects(@ModelAttribute("subject") @Valid LocalizedSubjectPojo
                                                  subjectPojo, BindingResult result, ModelMap model)
            throws UnsupportedEncodingException
    {
        if(result.hasErrors())
        {
            model.addAttribute("subjects", subjectService.getAllSubjects());
            model.addAttribute("languages", languageService.getAllLanguages());
            return new ModelAndView("localizedSubjects", "subject", subjectPojo);
        }

        //transform subject translation encoding from ISO-8859-1 to UTF-8
        //otherwise text will not be readable
        subjectPojo.setSubjectTranslation(new String(subjectPojo
                .getSubjectTranslation().getBytes("ISO-8859-1"), "UTF-8"));
        LocalizedSubject subject = new LocalizedSubject(subjectPojo.getId(),
                null, subjectPojo.getSubjectTranslation(), null);
        subjectService.setSubjectAndLangAndSaveLocalization
                (subjectPojo.getSubjectName(), subjectPojo.getLanguageAbbrev(), subject);
        return new ModelAndView("redirect:/admin/subjects");
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getLocalization(@RequestParam("name") String subjectName,
                                  @RequestParam("lang") String languageAbbrev)
    {
        Map<String, String> result = new HashMap<>();
        LocalizedSubject subject = subjectService.getSubjectLocalization(subjectName, languageAbbrev);
        if(subject != null)
        {
            result.put("translation", subject.getSubjectTranslation());
            result.put("id", String.valueOf(subject.getId()));
        }

        return result;
    }
}
