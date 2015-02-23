package ru.ildar.services;

import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Language;

import java.util.List;

@Service
public interface LanguageService
{
    /**
     * Returns all languages names(not abbreviations) from the database
     */
    List<String> getAllLanguagesValues();

    /**
     * Returns all languages from the database
     */
    List<Language> getAllLanguages();

    /**
     * Returns language specified by its abbreviation.
     * For example if abbreviation is en, then 'English' is returned.
     * @param abbrev The language abbreviation
     */
    Language getLanguageByAbbreviation(String abbrev);

    Language getLanguages(BooleanExpression expr);
}
