package ru.ildar.controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.StudentGradePojo;
import ru.ildar.database.entities.*;
import ru.ildar.services.GradeService;
import ru.ildar.services.StudentService;
import ru.ildar.services.TeacherService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/teacher")
public class TeacherController
{
    private TeacherService teacherService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        teacherService = serviceFactory.getTeacherService();
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    public ModelAndView teacherGroups(Principal principal, Locale locale)
    {
        List<TeachersGroups> tGroups = teacherService.getTeachersGroups(principal.getName());
        teacherService.setTranslationToSubjects(tGroups, locale.getLanguage());
        return new ModelAndView("teacher/group/groups", "groups", tGroups);
    }
}
