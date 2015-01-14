package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.*;
import ru.ildar.services.CityService;
import ru.ildar.services.GroupService;
import ru.ildar.services.SubjectService;
import ru.ildar.services.TeacherService;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class TeachersController
{
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CityService cityService;

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
        return new ModelAndView("adminTeachers");
    }

    @RequestMapping(value = "/admin/subjectTeachers", method = RequestMethod.GET)
    public ModelAndView subjectTeachers(@RequestParam("subject") String subjectName, ModelMap model)
    {
        Set<Teacher> subjTeachers = teacherService.getTeachersBySubject(subjectName);
        model.addAttribute("teachers", subjTeachers);
        model.addAttribute("subject", subjectName);
        return new ModelAndView("adminTeachers");
    }


    private ModelAndView teachersGroupsGeneric(ModelMap model, TaughtGroup group)
    {
        Iterable<City> cities = cityService.getAllCities();
        List<City> citiesList = new ArrayList<>();
        cities.forEach(citiesList::add);

        model.addAttribute("cities", cities);

        return new ModelAndView("teachersGroups", "taughtGroup", group == null ? new TaughtGroup() : group);
    }

    @RequestMapping(value = "/admin/teachers/groups", method = RequestMethod.GET)
    public ModelAndView teachersGroups(ModelMap model)
    {
        return teachersGroupsGeneric(model, null);
    }

    @RequestMapping(value = "/admin/teachers/groups", method = RequestMethod.POST)
    public ModelAndView teachersGroups(ModelMap model, @ModelAttribute("taughtGroup") TaughtGroup tGroup)
    {
        List<TeachersGroups> tGroups = teacherService.getTeachersGroups(tGroup.getTeacherSelect());
        model.addAttribute("tGroups", tGroups);
        return teachersGroupsGeneric(model, tGroup);
    }

    @RequestMapping(value = "/admin/teachers/getTeachers", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachers(@RequestParam("uniId") int uniId)
    {
        List<Teacher> teachers = teacherService.getTeachersByUniversity(uniId);
        teachers.forEach((t) -> t.setUniversity(null));
        return teachers;
    }


    @RequestMapping(value = "/admin/teachers/groups/add", method = RequestMethod.GET)
    public ModelAndView addTeachersGroups(ModelMap model)
    {
        List<Subject> subjects = subjectService.getAllSubjects();
        List<String> subjectsStr = new ArrayList<>();
        subjects.stream().map(Subject::getSubjectName).forEach(subjectsStr::add);

        model.addAttribute("subjects", subjectsStr);
        return new ModelAndView("addTeachersGroups", "tgroup", new TeachersGroupsPojo());
    }

    @RequestMapping(value ="/admin/teachers/groups/add", method = RequestMethod.POST)
    public ModelAndView addTeachersGroups(@ModelAttribute("tgroup") TeachersGroupsPojo tgroup)
    {
        TeachersGroups tg = new TeachersGroups(tgroup.getSubjectName(), tgroup.getSemester(), null, null);
        teacherService.setGroupAndTeacherAndAddTeachersGroups(tg,tgroup.getGroupSelect(), tgroup.getTeacher());
        return new ModelAndView("redirect:/admin/teachers");
    }

    @RequestMapping(value = "/admin/teachers/groups/add/{subject}", method = RequestMethod.GET)
    public ModelAndView addTeachersGroups(@PathVariable("subject") String subjectName)
    {
        TeachersGroupsPojo tg = new TeachersGroupsPojo();
        tg.setSubjectName(subjectName);
        return new ModelAndView("addTeachersGroups", "tgroup", tg);
    }
}
