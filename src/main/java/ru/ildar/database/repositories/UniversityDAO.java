package ru.ildar.database.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.University;

/**
 * Repository for performing CRUD operations on the UNIVERSITIES table
 */
public interface UniversityDAO extends PagingAndSortingRepository<University, Integer>,
        QueryDslPredicateExecutor<University>
{
}
