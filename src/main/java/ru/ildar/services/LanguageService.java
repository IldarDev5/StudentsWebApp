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

    public List<String> getAllLanguages()
    {
        List<String> result = new ArrayList<>();
//        languageDAO.findAll().forEach(result::add);
        for(String str : languageDAO.findAll())
            result.add(str);
        return result;
    }

    public String getLanguageByAbbreviation(String abbrev)
    {
        return languageDAO.findLanguageByAbbreviation(abbrev);
    }
}
