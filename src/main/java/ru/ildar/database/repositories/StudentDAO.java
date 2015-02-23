package ru.ildar.database.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Student;

import java.util.List;

/**
 * Repository for performing CRUD operations on the STUDENTS table
 */
public interface StudentDAO extends CrudRepository<Student, String>, QueryDslPredicateExecutor<Student>
{
}
