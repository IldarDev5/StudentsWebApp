package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.repositories.LanguageDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageService
{
    @Autowired
    private LanguageDAO languageDAO;

    /**
     * Returns all languages from the database
     */
    public List<String> getAllLanguages()
    {
        List<String> result = new ArrayList<>();
        languageDAO.findAll().forEach(result::add);
        return result;
    }

    /**
     * Returns language specified by its abbreviation.
     * For example if abbreviation is en, then 'English' is returned.
     * @param abbrev The language abbreviation
     */
    public String getLanguageByAbbreviation(String abbrev)
    {
        return languageDAO.findLanguageByAbbreviation(abbrev);
    }
}