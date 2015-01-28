package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.TaughtGroup;
import ru.ildar.controllers.pojos.TeachersGroupsPojo;
import ru.ildar.database.entities.*;
import ru.ildar.services.CityService;
import ru.ildar.services.SubjectService;
import ru.ildar.services.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Administrator controller that handles CRUD operations with teachers
 * and teachers groups
 */
@Controller
@RequestMapping("/admin/teachers")
public class TeachersController
{
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CityService cityService;

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


    private ModelAndView teachersGroupsGeneric(ModelMap model, TaughtGroup group)
    {
        Iterable<City> cities = cityService.getAllCities();
        List<City> citiesList = new ArrayList<>();
        cities.forEach(citiesList::add);

        model.addAttribute("cities", citiesList);

        return new ModelAndView("teachersGroups", "taughtGroup", group == null ? new TaughtGroup() : group);
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public ModelAndView teachersGroups(ModelMap model)
    {
        return teachersGroupsGeneric(model, null);
    }

    @RequestMapping(value = "groups", method = RequestMethod.POST)
    public ModelAndView teachersGroups(@ModelAttribute("taughtGroup") TaughtGroup tGroup,
                                       BindingResult result, ModelMap model)
    {
        if(result.hasErrors())
        {
            return teachersGroupsGeneric(model, tGroup);
        }

        List<TeachersGroups> tGroups = teacherService.getTeachersGroups(tGroup.getTeacherSelect());
        model.addAttribute("tGroups", tGroups);
        return teachersGroupsGeneric(model, tGroup);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachers(@RequestParam("uniId") int uniId)
    {
        List<Teacher> teachers = teacherService.getTeachersByUniversity(uniId);
        teachers.forEach((t) -> t.setUniversity(null));
        return teachers;
    }


    @RequestMapping(value = "groups/add", method = RequestMethod.GET)
    public ModelAndView addTeachersGroups(@RequestParam(value = "subject", required = false)
                                              String subjectName, ModelMap model)
    {
        return addTGroups(subjectName, model, new TeachersGroupsPojo());
    }

    private ModelAndView addTGroups(String subjectName, ModelMap model, TeachersGroupsPojo tg)
    {
        if(subjectName == null)
        {
            List<Subject> subjects = subjectService.getAllSubjects();
            List<String> subjectsStr = new ArrayList<>();
            subjects.stream().map(Subject::getSubjectName).forEach(subjectsStr::add);
            model.addAttribute("subjects", subjectsStr);
        }
        else
            tg.setSubjectName(subjectName);

        return new ModelAndView("addTeachersGroups", "tgroup", tg);
    }

    @RequestMapping(value ="groups/add", method = RequestMethod.POST)
    public ModelAndView addTeachersGroups(@ModelAttribute("tgroup") @Valid TeachersGroupsPojo tgroup,
                                    BindingResult result, ModelMap model, HttpServletRequest req)
    {
        if(result.hasErrors())
        {
            return addTGroups(req.getParameter("subject"), model, tgroup);
        }

        TeachersGroups tg = new TeachersGroups(tgroup.getSubjectName(), tgroup.getSemester(), null, null);
        teacherService.setGroupAndTeacherAndAddTeachersGroups(tg,
                tgroup.getGroupSelect(), tgroup.getTeacherSelect());
        return new ModelAndView("redirect:/admin/teachers");
    }
}
