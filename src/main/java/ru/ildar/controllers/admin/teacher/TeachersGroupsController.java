package ru.ildar.controllers.admin.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.TaughtGroup;
import ru.ildar.controllers.pojos.TeachersGroupsPojo;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.Subject;
import ru.ildar.database.entities.TeachersGroups;
import ru.ildar.services.CityService;
import ru.ildar.services.SubjectService;
import ru.ildar.services.TeacherService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * Administrator controller that handles CRUD operations with teachers groups
 */
@Controller
@RequestMapping("/admin/teachers/groups")
public class TeachersGroupsController
{
    @Autowired
    private CityService cityService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;

    private ModelAndView teachersGroupsGeneric(ModelMap model, TaughtGroup group)
    {
        Iterable<City> cities = cityService.getAllCities();
        List<City> citiesList = new ArrayList<>();
        cities.forEach(citiesList::add);

        model.addAttribute("cities", citiesList);

        return new ModelAndView("teachersGroups", "taughtGroup", group == null ? new TaughtGroup() : group);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView teachersGroups(ModelMap model)
    {
        return teachersGroupsGeneric(model, null);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, params = {"get"})
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

    @RequestMapping(value = "add", method = RequestMethod.GET)
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

    @RequestMapping(value ="add", method = RequestMethod.POST)
    public ModelAndView addTeachersGroups(@ModelAttribute("tgroup") @Valid TeachersGroupsPojo tgroup,
                                          BindingResult result, ModelMap model, HttpServletRequest req)
    {
        if(result.hasErrors())
        {
            return addTGroups(req.getParameter("subject"), model, tgroup);
        }

        TeachersGroups tg = new TeachersGroups(tgroup.getSubjectName(), tgroup.getSemester(), null, null);
        try
        {
            teacherService.setGroupAndTeacherAndAddTeachersGroups
                    (tg, tgroup.getGroupSelect(), tgroup.getTeacherSelect());
        }
        catch(DuplicateKeyException exc)
        //This group already has this teacher teaching this subject in this semester.
        {
            model.addAttribute("tGroupExists", true);
            return addTGroups(req.getParameter("subject"), model, tgroup);
        }

        return new ModelAndView("redirect:/admin/teachers");
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeTGroups(@RequestParam("tGroupId") Integer tGroupId)
    {
        teacherService.removeTeachersGroups(tGroupId);
        return "ok";
    }
}
