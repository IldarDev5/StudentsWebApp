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

    public List<Subject> getAllSubjects()
    {
        Iterable<Subject> it = subjectDAO.findAll();
        List<Subject> result = new ArrayList<>();
        it.forEach(result::add);
        return result;
    }

    public List<Subject> getAllSubjects(int pageNumber, int subjectsPerPage)
    {
        Iterable<Subject> it = subjectDAO.findAll(new PageRequest(pageNumber, subjectsPerPage));
        List<Subject> result = new ArrayList<>();
        it.forEach(result::add);
        return result;
    }

    public int getPagesCount(int subjectsPerPage)
    {
        return (int)Math.ceil((double)subjectDAO.count() / subjectsPerPage);
    }

    public List<String> getSubjectTypes()
    {
        List<String> types = new ArrayList<>();
        subjectTypeDAO.findAll().forEach((t) -> types.add(t.getSubjectType()));
        return types;
    }

    public void addSubject(Subject subject)
    {
        subjectDAO.save(subject);
    }

    public boolean subjectNameExists(String subjectName)
    {
        return subjectDAO.exists(subjectName);
    }
}
