package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Subject;
import ru.ildar.services.SubjectService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class SubjectsController
{
    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ModelAndView subjects(ModelMap model)
    {
        return subjects(1, model);
    }

    @RequestMapping(value = "/subjects/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView subjects(@PathVariable("pageNumber") int pageNumber, ModelMap model)
    {
        int SUBJECTS_PER_PAGE = 25;

        List<Subject> subjects = subjectService.getAllSubjects(pageNumber - 1, SUBJECTS_PER_PAGE);
        model.addAttribute("subjects", subjects);
        model.addAttribute("pagesCount", subjectService.getPagesCount(SUBJECTS_PER_PAGE));
        model.addAttribute("pageNumber", pageNumber);
        return new ModelAndView("subjects");
    }

    @RequestMapping(value = "/subjects/add", method = RequestMethod.GET)
    public ModelAndView addNewSubject(ModelMap model)
    {
        model.addAttribute("subject", new Subject());
        model.addAttribute("subjectTypes", subjectService.getSubjectTypes());
        return new ModelAndView("addSubject");
    }

    @RequestMapping(value = "/subjects/add", method = RequestMethod.POST)
    public ModelAndView addNewSubject(@ModelAttribute("subject") Subject subject)
    {
        subjectService.addSubject(subject);
        return new ModelAndView("redirect:/admin/subjects");
    }

    @RequestMapping(value = "/subjects/checkName", method = RequestMethod.GET,
            produces = "application/json")
    @ResponseBody
    public String checkSubjectName(@RequestParam("name") String subjectName)
    {
        boolean exists = subjectService.subjectNameExists(subjectName);
        return exists ? "{exists:true}" : "{exists:false}";
    }

    @RequestMapping(value = "/subjects/remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeSubject(@RequestParam("subjectName") String subjectName)
    {
        System.out.println("Kinda removed");
        return "true";
    }
}
