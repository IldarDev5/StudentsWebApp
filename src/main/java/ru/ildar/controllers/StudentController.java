package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Person;
import ru.ildar.services.GradeService;

import java.security.Principal;
import java.util.Set;

@Controller("/stud")
public class StudentController
{
    @Autowired
    private GradeService gradeService;

    @RequestMapping("/grades")
    public ModelAndView studGrades(Principal principal)
    {
        Set<Long> semesters = gradeService.getStudentSemesters(principal.getName());
        return new ModelAndView("stud/grades", "semesters", semesters);
    }

    @RequestMapping("/teachers")
    public ModelAndView studTeachers(Principal principal)
    {
        String studName = principal.getName();
        Set<Person> teachers = gradeService.getStudentTeachers(studName);
        return new ModelAndView("stud/teachers", "teachers", teachers);
    }
}
