package ru.ildar.database.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.SubjectType;

/**
 * Repository for performing CRUD operations on the SUBJECT_TYPES table
 */
public interface SubjectTypeDAO extends CrudRepository<SubjectType, String>,
        QueryDslPredicateExecutor<SubjectType>
{
}
