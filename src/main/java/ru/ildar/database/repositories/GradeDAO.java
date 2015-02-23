package ru.ildar.database.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Grade;

import java.util.List;

/**
 * Repository for performing CRUD operations on the GRADES table
 */
public interface GradeDAO extends CrudRepository<Grade, Integer>, QueryDslPredicateExecutor<Grade>
{
}
