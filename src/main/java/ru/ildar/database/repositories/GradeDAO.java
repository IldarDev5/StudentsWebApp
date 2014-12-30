package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Person;

import java.util.List;

public interface GradeDAO extends CrudRepository<Grade, Long>
{
    List<Grade> findByStudentAndSemester(Person student, long semester);
    List<Grade> findByStudent_Username(String userName);
}
