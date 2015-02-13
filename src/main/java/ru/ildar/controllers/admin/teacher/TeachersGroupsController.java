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
import ru.ildar.database.entities.Subject;
import ru.ildar.database.entities.TeachersGroups;
import ru.ildar.services.CityService;
import ru.ildar.services.SubjectService;
import ru.ildar.services.TeacherService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Administrator controller that handles CRUD operations with teachers groups
 */
@Controller
@RequestMapping("/admin/teachers/groups")
public class TeachersGroupsController
{
    private CityService cityService;
    private TeacherService teacherService;
    private SubjectService subjectService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        cityService = serviceFactory.getCityService();
        teacherService = serviceFactory.getTeacherService();
        subjectService = serviceFactory.getSubjectService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView teachersGroups(ModelMap model)
    {
        model.addAttribute("cities", cityService.getAllCities());
        return new ModelAndView("admin/teacher/teachersGroups", "taughtGroup", new TaughtGroup());
    }

    @RequestMapping(value = "", method = RequestMethod.GET, params = {"get"})
    public ModelAndView teachersGroups(@ModelAttribute("taughtGroup") TaughtGroup tGroup,
                                       BindingResult result, ModelMap model)
    {
        model.addAttribute("cities", cityService.getAllCities());
        if(result.hasErrors())
        {
            return new ModelAndView("admin/teacher/teachersGroups", "taughtGroup", tGroup);
        }

        model.addAttribute("tGroups", teacherService.getTeachersGroups(tGroup.getTeacherSelect()));
        return new ModelAndView("admin/teacher/teachersGroups", "taughtGroup", tGroup);
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
            //If there's no selected subject, add subjects' list so user can choose the subject
        {
            List<Subject> subjects = subjectService.getAllSubjects();
            List<String> subjectsStr = new ArrayList<>();
            subjects.stream().map(Subject::getSubjectName).forEach(subjectsStr::add);
            model.addAttribute("subjects", subjectsStr);
        }
        else
            tg.setSubjectName(subjectName);

        return new ModelAndView("admin/teacher/addTeachersGroups", "tgroup", tg);
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
    public Map<String, Object> removeTGroups(@RequestParam("tGroupId") Integer tGroupId)
    {
        Map<String, Object> result = new HashMap<>();
        teacherService.removeTeachersGroups(tGroupId);
        result.put("removed", true);
        return result;
    }
}
