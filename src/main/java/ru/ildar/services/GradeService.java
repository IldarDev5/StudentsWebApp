package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.PersonDetails;
import ru.ildar.database.repositories.GradeDAO;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

    public Set<Long> getStudentSemesters(String studUsername)
    {
        List<Grade> grades = gradeDAO.findByStudent_Username(studUsername);
        Set<Long> results = new TreeSet<>();
        grades.stream().map(Grade::getSemester).forEach(results::add);

        return results;
    }

    public Set<Person> getStudentTeachers(String studName)
    {
        List<Grade> grades = gradeDAO.findByStudent_Username(studName);
        Set<Person> teachers = new TreeSet<>(personComparator());
        grades.stream().map(Grade::getTeacher).forEach(teachers::add);

        return teachers;
    }

    private Comparator<? super Person> personComparator()
    {
        return (p1, p2) ->
        {
            PersonDetails pd1 = p1.getDetails();
            PersonDetails pd2 = p2.getDetails();

            if(!pd1.getFirstName().equals(pd2.getFirstName()))
                return pd1.getFirstName().compareTo(pd2.getFirstName());
            else
                return pd1.getLastName().compareTo(pd2.getLastName());
        };
    }
}
