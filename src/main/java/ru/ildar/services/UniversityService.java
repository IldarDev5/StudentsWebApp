package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.University;
import ru.ildar.database.repositories.UniversityDAO;

import java.util.List;

@Service
public class UniversityService
{
    @Autowired
    private UniversityDAO universityDAO;

    public void addUniversity(University un)
    {
        universityDAO.save(un);
    }

    public University getById(long id)
    {
        return universityDAO.findOne(id);
    }

    public University getByName(String unName)
    {
        return universityDAO.findByUnName(unName);
    }

    public List<University> getUniversities(int pageNumber, int unisPerPage)
    {
        return universityDAO.find(new PageRequest(pageNumber, unisPerPage));
    }

    public int getPagesCount(int unisPerPage)
    {
        return (int)Math.ceil((double)universityDAO.count() / unisPerPage);
    }
}
