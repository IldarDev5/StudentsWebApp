package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Subject;
import ru.ildar.database.entities.SubjectType;
import ru.ildar.database.repositories.SubjectDAO;
import ru.ildar.database.repositories.SubjectTypeDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService
{
    @Autowired
    private SubjectDAO subjectDAO;
    @Autowired
    private SubjectTypeDAO subjectTypeDAO;

    /**
     * Returns all subjects from the database
     */
    public List<Subject> getAllSubjects()
    {
        Iterable<Subject> it = subjectDAO.findAll();
        List<Subject> result = new ArrayList<>();
        for(Subject subj : it)
            result.add(subj);
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
        for(Subject subj : it)
            result.add(subj);
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
        for(SubjectType type : subjectTypeDAO.findAll())
            types.add(type.getSubjectType());
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
}
