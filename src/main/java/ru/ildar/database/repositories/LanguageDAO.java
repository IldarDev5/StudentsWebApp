package ru.ildar.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import ru.ildar.database.entities.Language;

public interface LanguageDAO extends Repository<Language, String>
{
    @Query("select l.language from Language l")
    Iterable<String> findAll();

    @Query("select l.language from Language l where l.abbreviation = :abbrev")
    String findLanguageByAbbreviation(@Param("abbrev") String abbrev);
}
