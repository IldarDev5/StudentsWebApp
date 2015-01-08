package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Teacher;
import ru.ildar.services.TeacherService;

import java.util.List;
import java.util.Set;

@Controller
public class TeachersController
{
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/admin/teachers", method = RequestMethod.GET)
    public ModelAndView teachers(ModelMap model)
    {
        return teachers(1, model);
    }

    @RequestMapping(value = "/admin/teachers/{pageNumber}", method = RequestMethod.GET)
    public ModelAndView teachers(@PathVariable("pageNumber") int pageNumber, ModelMap model)
    {
        int TEACHERS_PER_PAGE = 25;
        List<Teacher> teachers = teacherService.getTeachers(pageNumber - 1, TEACHERS_PER_PAGE);
        model.addAttribute("teachers", teachers);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pagesCount", teacherService.getTeachersPagesCount(TEACHERS_PER_PAGE));
        return new ModelAndView("teachers");
    }

    @RequestMapping(value = "/admin/subjectTeachers", method = RequestMethod.GET)
    public ModelAndView subjectTeachers(@RequestParam("subject") String subjectName, ModelMap model)
    {
        Set<Teacher> subjTeachers = teacherService.getTeachersBySubject(subjectName);
        model.addAttribute("teachers", subjTeachers);
        model.addAttribute("subject", subjectName);
        return new ModelAndView("teachers");
    }
}
