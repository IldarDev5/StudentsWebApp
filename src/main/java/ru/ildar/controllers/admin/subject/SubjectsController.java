package ru.ildar.controllers.admin.subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.LocalizedSubject;
import ru.ildar.database.entities.Subject;
import ru.ildar.services.SubjectService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Administrator controller that handles CRUD operations with subjects
 */
@Controller
@RequestMapping("/admin/subjects")
public class SubjectsController
{
    private SubjectService subjectService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        subjectService = serviceFactory.getSubjectService();
    }

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
        return new ModelAndView("admin/subject/subjects");
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addNewSubject(ModelMap model)
    {
        model.addAttribute("subjectTypes", subjectService.getSubjectTypes());
        return new ModelAndView("admin/subject/addSubject", "subject", new Subject());
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
        //Add also default localization for this subject
        subjectService.setLanguageAndSaveLocalization(Locale.US.getLanguage(), new LocalizedSubject(null, subject, subject.getSubjectName(), null));

        return new ModelAndView("redirect:/admin/subjects");
    }

    /**
     * Checks if a subject with such name exists in the database
     */
    @RequestMapping(value = "checkName", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public Map<String, String> checkSubjectName(@RequestParam("name") String subjectName)
    {
        Map<String, String> result = new HashMap<>();
        boolean exists = subjectService.subjectNameExists(subjectName);
        result.put("exists", String.valueOf(exists));
        return result;
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> removeSubject(@RequestParam("subjectName") String subjectName)
    {
        Map<String, Object> result = new HashMap<>();
        subjectService.removeSubject(subjectName);
        result.put("removed", true);
        return result;
    }
}
