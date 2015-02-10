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
import java.util.List;
import java.util.Locale;

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
        //Add also default localization for this subject
        subjectService.setLanguageAndSaveLocalization(Locale.US.getLanguage(), new LocalizedSubject(null, subject, subject.getSubjectName(), null));

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
}
