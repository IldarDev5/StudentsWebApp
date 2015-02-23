package ru.ildar.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.ildar.database.entities.Language;

/**
 * Repository for performing CRUD operations on the LANGUAGES table
 */
public interface LanguageDAO extends Repository<Language, String>, QueryDslPredicateExecutor<Language>
{
    @Query("select l.language from Language l")
    Iterable<String> findAllValues();

    Iterable<Language> findAll();
}
