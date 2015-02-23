package ru.ildar.services.impl.jpa;

import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.LocalizedSubject;
import ru.ildar.database.entities.QLocalizedSubject;
import ru.ildar.database.entities.Subject;
import ru.ildar.database.repositories.LocalizedSubjectDAO;
import ru.ildar.database.repositories.SubjectDAO;
import ru.ildar.database.repositories.SubjectTypeDAO;
import ru.ildar.services.SubjectService;
import ru.ildar.services.factory.impl.JpaServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static ru.ildar.CollectionsUtil.getListFromIterable;

@Service
public class SubjectServiceJpaImpl implements SubjectService
{
    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private SubjectTypeDAO subjectTypeDAO;
    @Autowired
    private LocalizedSubjectDAO localizedSubjectDAO;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public List<Subject> getAllSubjects()
    {
        return getListFromIterable(subjectDAO.findAll());
    }

    @Override
    public List<Subject> getAllSubjects(int pageNumber, int subjectsPerPage)
    {
        return getListFromIterable(subjectDAO.findAll(new PageRequest(pageNumber, subjectsPerPage)));
    }

    @Override
    public int getPagesCount(int subjectsPerPage)
    {
        return (int)Math.ceil((double)subjectDAO.count() / subjectsPerPage);
    }

    @Override
    public Set<String> getSubjectTypes()
    {
        Set<String> types = new TreeSet<>();
        subjectTypeDAO.findAll().forEach((t) -> types.add(t.getSubjectType()));
        return types;
    }

    @Override
    public void addSubject(Subject subject)
    {
        subjectDAO.save(subject);
    }

    @Override
    public boolean subjectNameExists(String subjectName)
    {
        return subjectDAO.exists(subjectName);
    }

    @Override
    public void removeSubject(String subjectName)
    {
        subjectDAO.delete(subjectName);
    }

    @Override
    public void setSubjectAndLangAndSaveLocalization(String subjectName,
                                                     String langAbbrev, LocalizedSubject locSubject)
    {
        locSubject.setSubject(subjectDAO.findOne(subjectName));
        locSubject.setLanguage(serviceFactory.getLanguageService().getLanguageByAbbreviation(langAbbrev));
        localizedSubjectDAO.save(locSubject);
    }

    @Override
    public LocalizedSubject getSubjectLocalization(String subjectName, String languageAbbrev)
    {
        QLocalizedSubject locSubj = QLocalizedSubject.localizedSubject;
        BooleanExpression expr = locSubj.subject.subjectName.eq(subjectName)
                .and(locSubj.language.abbreviation.eq(languageAbbrev));
        return localizedSubjectDAO.findOne(expr);
    }

    @Override
    public void removeSubjectLocalization(Integer id)
    {
        localizedSubjectDAO.delete(id);
    }

    @Override
    public void setLanguageAndSaveLocalization(String languageAbbrev, LocalizedSubject locSubject)
    {
        locSubject.setLanguage(serviceFactory.getLanguageService().getLanguageByAbbreviation(languageAbbrev));
        localizedSubjectDAO.save(locSubject);
    }
}
