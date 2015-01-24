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
import ru.ildar.database.entities.Group;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.Teacher;
import ru.ildar.services.GradeService;
import ru.ildar.services.GroupService;
import ru.ildar.services.TeacherService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Student controller that handles read operations with
 * student grades and teachers
 */
@Controller
@RequestMapping("/stud")
public class StudentController
{
    @Autowired
    private GradeService gradeService;
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "grades", method = RequestMethod.GET)
    public ModelAndView studGrades(Principal principal)
    {
        Set<Long> semesters = gradeService.getStudentSemesters(principal.getName());
        return new ModelAndView("grades", "semesters", semesters);
    }

    @RequestMapping(value = "semesterGrades", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Grade> semesterGrades(@RequestParam("sem") long semester, Principal principal)
    {
        List<Grade> grades = gradeService.getStudentGradesInSemester(principal.getName(), semester);
//        grades.stream().forEach((g) -> { g.setStudent(null); g.getTeacher().setUniversity(null); });
        for(Grade grade : grades)
        {
            grade.setStudent(null);
            grade.getTeacher().setUniversity(null);
        }
        return grades;
    }

    @RequestMapping(value = "studTeachers", method = RequestMethod.GET)
    public ModelAndView studTeachers(Principal principal)
    {
        String studName = principal.getName();
        Map<Teacher, Set<String>> teachers = teacherService.getStudentTeachers(studName);

        Map<Teacher, String> teachersSubjs = new HashMap<>();
//        teachers.entrySet().stream().forEach((t) ->
//        {
//            teachersSubjs.put(t.getKey(), t.getValue().stream().reduce((s, s2) -> s + ", " + s2).get());
//        });
        for(Map.Entry<Teacher, Set<String>> teacher : teachers.entrySet())
            teachersSubjs.put(teacher.getKey(), teacher.getValue().toString());

        return new ModelAndView("studTeachers", "teachers", teachersSubjs);
    }
}
