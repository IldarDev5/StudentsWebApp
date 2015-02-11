package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.LocalizedSubject;
import ru.ildar.database.entities.Subject;

import java.util.List;
import java.util.Set;

@Service
public interface SubjectService
{
    /**
     * Returns all subjects from the database
     */
    List<Subject> getAllSubjects();

    /**
     * Returns subjects from the specified page
     * @param pageNumber Number of the page
     * @param subjectsPerPage Amount of subjects on a single page
     */
    List<Subject> getAllSubjects(int pageNumber, int subjectsPerPage);

    /**
     * Rounding method for pages count
     */
    int getPagesCount(int subjectsPerPage);

    /**
     * Returns list of the subjects types
     */
    Set<String> getSubjectTypes();
    /**
     * Add a subject to the database
     */
    void addSubject(Subject subject);

    /**
     * Check if such subject exists in the database
     */
    boolean subjectNameExists(String subjectName);

    /**
     * Remove the subject specified by its name
     */
    void removeSubject(String subjectName);

    /**
     * Save localization, but before this set subject specified by its name and
     * language specified by its abbreviation
     * @param subjectName Name of the subject whose localization to save
     * @param langAbbrev Abbreviation of the language
     * @param locSubject Localized subject object to save
     */
    void setSubjectAndLangAndSaveLocalization(String subjectName,
                                                     String langAbbrev, LocalizedSubject locSubject);

    /**
     * Returns localization of a subject
     * @param subjectName Name of the subject whose localization to return
     * @param languageAbbrev Abbreviation of the language which is the localization's language
     */
    LocalizedSubject getSubjectLocalization(String subjectName, String languageAbbrev);

    /**
     * Remove subject localization by the localization ID
     */
    void removeSubjectLocalization(Integer id);

    void setLanguageAndSaveLocalization(String languageAbbrev, LocalizedSubject locSubject);
}
