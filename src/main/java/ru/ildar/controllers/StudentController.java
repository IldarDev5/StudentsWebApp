package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Person;
import ru.ildar.services.GradeService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class StudentController
{
    @Autowired
    private GradeService gradeService;

    @RequestMapping(value = "stud/grades", method = RequestMethod.GET)
    public ModelAndView studGrades(Principal principal)
    {
        Set<Long> semesters = gradeService.getStudentSemesters(principal.getName());
        return new ModelAndView("grades", "semesters", semesters);
    }

    @RequestMapping(value = "stud/studTeachers", method = RequestMethod.GET)
    public ModelAndView studTeachers(Principal principal)
    {
        String studName = principal.getName();
        Set<Person> teachers = gradeService.getStudentTeachers(studName);
        return new ModelAndView("studTeachers", "teachers", teachers);
    }

    @RequestMapping(value = "stud/semesterGrades", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Grade> semesterGrades(@RequestParam("sem") long semester, Principal principal)
    {
        return gradeService.getStudentGradesInSemester(principal.getName(), semester);
    }
}
