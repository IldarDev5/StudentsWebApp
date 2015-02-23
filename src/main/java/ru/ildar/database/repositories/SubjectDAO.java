package ru.ildar.database.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.Subject;

/**
 * Repository for performing CRUD operations on the SUBJECTS table
 */
public interface SubjectDAO extends PagingAndSortingRepository<Subject, String>,
        QueryDslPredicateExecutor<Subject>
{
}
