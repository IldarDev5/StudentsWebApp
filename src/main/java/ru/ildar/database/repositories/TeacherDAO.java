package ru.ildar.database.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.Teacher;

import java.util.List;

/**
 * Repository for performing CRUD operations on the TEACHERS table
 */
public interface TeacherDAO extends PagingAndSortingRepository<Teacher, String>,
        QueryDslPredicateExecutor<Teacher>
{
}
