package ru.ildar.database.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.LocalizedSubject;

public interface LocalizedSubjectDAO extends CrudRepository<LocalizedSubject, Integer>,
        QueryDslPredicateExecutor<LocalizedSubject>
{
}
