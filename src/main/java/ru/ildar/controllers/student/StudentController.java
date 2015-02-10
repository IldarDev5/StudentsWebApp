package ru.ildar.controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Teacher;
import ru.ildar.services.GradeService;
import ru.ildar.services.TeacherService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.*;

/**
 * Student controller that handles read operations with
 * student grades and teachers
 */
@Controller
@RequestMapping("/stud")
public class StudentController
{
    private GradeService gradeService;
    private TeacherService teacherService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        gradeService = serviceFactory.getGradeService();
        teacherService = serviceFactory.getTeacherService();
    }

    @RequestMapping(value = "grades", method = RequestMethod.GET)
    public ModelAndView studGrades(Principal principal)
    {
        Set<Long> semesters = gradeService.getStudentSemesters(principal.getName());
        return new ModelAndView("grades", "semesters", semesters);
    }

    @RequestMapping(value = "semesterGrades", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Grade> semesterGrades(@RequestParam("sem") long semester, Principal principal, Locale locale)
    {
        List<Grade> grades = gradeService.getStudentGradesInSemester(principal.getName(), semester);
        //Set the subject translation so user can see subject name in his own language
        //If the subject translation for this locale doesn't exist, default subject name
        //will be displayed
        gradeService.setTranslationToGradeSubjects(grades, locale.getLanguage());
        //Setting some properties to null so that Jackson JSON mapper wouldn't give error
        //because of circular object dependencies
        grades.stream().forEach((g) -> { g.setStudent(null); g.getTeacher().setUniversity(null); });
        return grades;
    }

    @RequestMapping(value = "studTeachers", method = RequestMethod.GET)
    public ModelAndView studTeachers(Principal principal, Locale locale)
    {
        String studName = principal.getName();
        Map<Teacher, Set<String>> teachers = teacherService.getStudentTeachers(studName, locale);

        Map<Teacher, String> teachersSubjs = new HashMap<>();
        teachers.entrySet().stream().forEach((t) ->
        {
            teachersSubjs.put(t.getKey(), t.getValue().stream().reduce((s, s2) -> s + ", " + s2).get());
        });

        return new ModelAndView("studTeachers", "teachers", teachersSubjs);
    }
}
