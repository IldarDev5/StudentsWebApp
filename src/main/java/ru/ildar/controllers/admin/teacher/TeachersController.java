package ru.ildar.controllers.admin.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.*;
import ru.ildar.services.TeacherService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * Administrator controller that handles CRUD operations with teachers
 */
@Controller
@RequestMapping("/admin/teachers")
public class TeachersController
{
    private TeacherService teacherService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        teacherService = serviceFactory.getTeacherService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView teachers(ModelMap model)
    {
        return teachers(1, model);
    }

    @RequestMapping(value = "{pageNumber}", method = RequestMethod.GET)
    public ModelAndView teachers(@PathVariable("pageNumber") int pageNumber, ModelMap model)
    {
        int TEACHERS_PER_PAGE = 25;
        List<Teacher> teachers = teacherService.getTeachers(pageNumber - 1, TEACHERS_PER_PAGE);
        model.addAttribute("teachers", teachers);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("pagesCount", teacherService.getTeachersPagesCount(TEACHERS_PER_PAGE));
        return new ModelAndView("adminTeachers");
    }

    @RequestMapping(value = "bySubject", method = RequestMethod.GET)
    public ModelAndView subjectTeachers(@RequestParam("subject") String subjectName, ModelMap model)
    {
        Set<Teacher> subjTeachers = teacherService.getTeachersBySubject(subjectName);
        model.addAttribute("teachers", subjTeachers);
        model.addAttribute("subject", subjectName);
        return new ModelAndView("adminTeachers");
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachers(@RequestParam("uniId") int uniId)
    {
        List<Teacher> teachers = teacherService.getTeachersByUniversity(uniId);
        teachers.forEach((t) -> t.setUniversity(null));
        return teachers;
    }
}
