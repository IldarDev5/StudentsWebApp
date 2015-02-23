package ru.ildar.services.impl.jpa;

import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Language;
import ru.ildar.database.entities.QLanguage;
import ru.ildar.database.repositories.LanguageDAO;
import ru.ildar.services.LanguageService;

import java.util.ArrayList;
import java.util.List;

@Service
public class LanguageServiceJpaImpl implements LanguageService
{
    @Autowired
    private LanguageDAO languageDAO;

    @Override
    public List<String> getAllLanguagesValues()
    {
        List<String> result = new ArrayList<>();
        languageDAO.findAllValues().forEach(result::add);
        return result;
    }

    @Override
    public List<Language> getAllLanguages()
    {
        List<Language> result = new ArrayList<>();
        languageDAO.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Language getLanguageByAbbreviation(String abbrev)
    {
        return languageDAO.findOne(QLanguage.language1.abbreviation.eq(abbrev));
    }

    @Override
    public Language getLanguages(BooleanExpression expr)
    {
        return languageDAO.findOne(expr);
    }
}
