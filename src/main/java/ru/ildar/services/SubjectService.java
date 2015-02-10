package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Language;
import ru.ildar.database.entities.LocalizedSubject;
import ru.ildar.database.entities.Subject;
import ru.ildar.database.repositories.LanguageDAO;
import ru.ildar.database.repositories.LocalizedSubjectDAO;
import ru.ildar.database.repositories.SubjectDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService
{
    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private LocalizedSubjectDAO localizedSubjectDAO;
    @Autowired
    private LanguageService languageService;

    /**
     * Returns all subjects from the database
     */
    public List<Subject> getAllSubjects()
    {
        Iterable<Subject> it = subjectDAO.findAll();
        List<Subject> result = new ArrayList<>();
        it.forEach(result::add);
        return result;
    }

    /**
     * Returns subjects from the specified page
     * @param pageNumber Number of the page
     * @param subjectsPerPage Amount of subjects on a single page
     */
    public List<Subject> getAllSubjects(int pageNumber, int subjectsPerPage)
    {
        Iterable<Subject> it = subjectDAO.findAll(new PageRequest(pageNumber, subjectsPerPage));
        List<Subject> result = new ArrayList<>();
        it.forEach(result::add);
        return result;
    }

    /**
     * Rounding method for pages count
     */
    public int getPagesCount(int subjectsPerPage)
    {
        return (int)Math.ceil((double)subjectDAO.count() / subjectsPerPage);
    }

    /**
     * Returns list of the subjects types
     */
    public List<String> getSubjectTypes()
    {
        List<String> types = new ArrayList<>();
        subjectDAO.findAll().forEach((t) -> types.add(t.getSubjectType()));
        return types;
    }

    /**
     * Add a subject to the database
     */
    public void addSubject(Subject subject)
    {
        subjectDAO.save(subject);
    }

    /**
     * Check if such subject exists in the database
     */
    public boolean subjectNameExists(String subjectName)
    {
        return subjectDAO.exists(subjectName);
    }

    /**
     * Remove the subject specified by its name
     */
    public void removeSubject(String subjectName)
    {
        subjectDAO.delete(subjectName);
    }

    /**
     * Save localization, but before this set subject specified by its name and
     * language specified by its abbreviation
     * @param subjectName Name of the subject whose localization to save
     * @param langAbbrev Abbreviation of the language
     * @param locSubject Localized subject object to save
     */
    public void setSubjectAndLangAndSaveLocalization(String subjectName,
                                                     String langAbbrev, LocalizedSubject locSubject)
    {
        locSubject.setSubject(subjectDAO.findOne(subjectName));
        locSubject.setLanguage(languageService.getLanguageByAbbreviation(langAbbrev));
        localizedSubjectDAO.save(locSubject);
    }

    /**
     * Returns localization of a subject
     * @param subjectName Name of the subject whose localization to return
     * @param languageAbbrev Abbreviation of the language which is the localization's language
     */
    public LocalizedSubject getSubjectLocalization(String subjectName, String languageAbbrev)
    {
        return localizedSubjectDAO.findBySubject_SubjectNameAndLanguage_Abbreviation
                (subjectName, languageAbbrev);
    }

    /**
     * Remove subject localization by the localization ID
     */
    public void removeSubjectLocalization(Integer id)
    {
        localizedSubjectDAO.delete(id);
    }

    public void setLanguageAndSaveLocalization(String languageAbbrev, LocalizedSubject locSubject)
    {
        locSubject.setLanguage(languageService.getLanguageByAbbreviation(languageAbbrev));
        localizedSubjectDAO.save(locSubject);
    }
}
