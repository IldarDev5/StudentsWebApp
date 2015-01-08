package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.GradeDAO;

import java.util.*;

@Service
public class GradeService
{
    @Autowired
    private GradeDAO gradeDAO;

    public List<Grade> getStudentGradesInSemester(String studUsername, long semester)
    {
        return gradeDAO.findByStudent_UsernameAndSemester(studUsername, semester);
    }

    public void addGrade(Grade grade)
    {
        gradeDAO.save(grade);
    }

    public Set<Long> getStudentSemesters(String studUsername)
    {
        List<Grade> grades = gradeDAO.findByStudent_Username(studUsername);
        Set<Long> results = new TreeSet<>();
        grades.stream().map(Grade::getSemester).forEach(results::add);

        return results;
    }
}
