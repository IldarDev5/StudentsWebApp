package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.LocalizedSubjectPojo;
import ru.ildar.database.entities.LocalizedSubject;
import ru.ildar.database.entities.Subject;
import ru.ildar.services.LanguageService;
import ru.ildar.services.SubjectService;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Administrator controller that handles CRUD operations with subjects
 */
@Controller
@RequestMapping("/admin/subjects")
public class SubjectsController
{
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView subjects(ModelMap model)
    {
        return subjects(1, model);
    }

    @RequestMapping(value = "{pageNumber}", method = RequestMethod.GET)
    public ModelAndView subjects(@PathVariable("pageNumber") int pageNumber, ModelMap model)
    {
        int SUBJECTS_PER_PAGE = 25;

        List<Subject> subjects = subjectService.getAllSubjects(pageNumber - 1, SUBJECTS_PER_PAGE);
        model.addAttribute("subjects", subjects);
        model.addAttribute("pagesCount", subjectService.getPagesCount(SUBJECTS_PER_PAGE));
        model.addAttribute("pageNumber", pageNumber);
        return new ModelAndView("subjects");
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addNewSubject(ModelMap model)
    {
        model.addAttribute("subjectTypes", subjectService.getSubjectTypes());
        return new ModelAndView("addSubject", "subject", new Subject());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addNewSubject(@ModelAttribute("subject") @Valid Subject subject,
                                      BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            model.addAttribute("subjectTypes", subjectService.getSubjectTypes());
            return new ModelAndView("addSubject", "subject", subject);
        }

        subjectService.addSubject(subject);
        return new ModelAndView("redirect:/admin/subjects");
    }

    @RequestMapping(value = "checkName", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String checkSubjectName(@RequestParam("name") String subjectName)
    {
        boolean exists = subjectService.subjectNameExists(subjectName);
        return exists ? "{exists:true}" : "{exists:false}";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeSubject(@RequestParam("subjectName") String subjectName)
    {
        subjectService.removeSubject(subjectName);
        return "ok";
    }

    @RequestMapping(value = "localized", method = RequestMethod.GET)
    public ModelAndView localizedSubjects(@RequestParam("subject") String subject, ModelMap model)
    {
        LocalizedSubjectPojo subjectPojo = new LocalizedSubjectPojo();
        subjectPojo.setSubjectName(subject);
        model.addAttribute("subjects", subjectService.getAllSubjects());
        model.addAttribute("languages", languageService.getAllLanguages());
        return new ModelAndView("localizedSubjects", "subject", subjectPojo);
    }

    @RequestMapping(value = "localized", method = RequestMethod.POST)
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

        subjectPojo.setSubjectTranslation(new String(subjectPojo
                .getSubjectTranslation().getBytes("ISO-8859-1"), "UTF-8"));
        LocalizedSubject subject = new LocalizedSubject(subjectPojo.getId(),
                null, subjectPojo.getSubjectTranslation(), null);
        subjectService.setSubjectAndLangAndSaveLocalization
                (subjectPojo.getSubjectName(), subjectPojo.getLanguageAbbrev(), subject);
        return new ModelAndView("redirect:/admin/subjects");
    }

    @RequestMapping(value = "getLocalization", method = RequestMethod.GET)
    @ResponseBody
    public String getLocalization(@RequestParam("name") String subjectName,
                                  @RequestParam("lang") String languageAbbrev)
    {
        LocalizedSubject subject = subjectService.getSubjectLocalization(subjectName, languageAbbrev);
        if(subject != null)
        {
            return "{ \"translation\":\"" + subject.getSubjectTranslation() + "\", " +
                    "\"id\":\"" + subject.getId() + "\" }";
        }
        else
            return "{}";
    }
}
