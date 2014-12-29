package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.GradeDAO;

import java.util.List;

@Service
public class GradeService
{
    @Autowired
    private GradeDAO gradeDAO;

    public List<Grade> getStudentGradesInSemester(Person student, int semester)
    {
        return gradeDAO.findByStudentAndSemester(student, semester);
    }

    public void addGrade(Grade grade)
    {
        gradeDAO.save(grade);
    }
}
