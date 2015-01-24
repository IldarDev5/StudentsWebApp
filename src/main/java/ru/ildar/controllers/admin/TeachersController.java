package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.TaughtGroup;
import ru.ildar.controllers.pojos.TeachersGroupsPojo;
import ru.ildar.database.entities.*;
import ru.ildar.services.CityService;
import ru.ildar.services.SubjectService;
import ru.ildar.services.TeacherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
//        cities.forEach(citiesList::add);
        for(City city : cities)
            citiesList.add(city);

        model.addAttribute("cities", citiesList);

        return new ModelAndView("teachersGroups", "taughtGroup", group == null ? new TaughtGroup() : group);
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public ModelAndView teachersGroups(ModelMap model)
    {
        return teachersGroupsGeneric(model, null);
    }

    @RequestMapping(value = "groups", method = RequestMethod.POST)
    public ModelAndView teachersGroups(ModelMap model, @ModelAttribute("taughtGroup") TaughtGroup tGroup)
    {
        List<TeachersGroups> tGroups = teacherService.getTeachersGroups(tGroup.getTeacherSelect());
        model.addAttribute("tGroups", tGroups);
        return teachersGroupsGeneric(model, tGroup);
    }

    @RequestMapping(value = "get", method = RequestMethod.GET)
    @ResponseBody
    public List<Teacher> getTeachers(@RequestParam("uniId") int uniId)
    {
        List<Teacher> teachers = teacherService.getTeachersByUniversity(uniId);
//        teachers.forEach((t) -> t.setUniversity(null));
        for(Teacher teacher : teachers)
            teacher.setUniversity(null);
        return teachers;
    }


    @RequestMapping(value = "groups/add", method = RequestMethod.GET)
    public ModelAndView addTeachersGroups(@RequestParam(value = "subject", required = false)
                                              String subjectName, ModelMap model)
    {
        TeachersGroupsPojo tg = new TeachersGroupsPojo();

        if(subjectName == null)
        {
            List<Subject> subjects = subjectService.getAllSubjects();
            List<String> subjectsStr = new ArrayList<>();
            //        subjects.stream().map(Subject::getSubjectName).forEach(subjectsStr::add);
            for (Subject subject : subjects)
                subjectsStr.add(subject.getSubjectName());
            model.addAttribute("subjects", subjectsStr);
        }
        else
            tg.setSubjectName(subjectName);
        return new ModelAndView("addTeachersGroups", "tgroup", tg);
    }

    @RequestMapping(value ="groups/add", method = RequestMethod.POST)
    public String addTeachersGroups(@ModelAttribute("tgroup") TeachersGroupsPojo tgroup)
    {
        TeachersGroups tg = new TeachersGroups(tgroup.getSubjectName(), tgroup.getSemester(), null, null);
        teacherService.setGroupAndTeacherAndAddTeachersGroups(tg,
                tgroup.getGroupSelect(), tgroup.getTeacherSelect());
        return "redirect:/admin/teachers";
    }
}
