package ru.ildar.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.University;
import ru.ildar.database.entities.UniversityDescription;
import ru.ildar.database.repositories.CityDAO;
import ru.ildar.database.repositories.UniDescriptionDAO;
import ru.ildar.database.repositories.UniversityDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService
{
    @Autowired
    private UniversityDAO universityDAO;
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private UniDescriptionDAO uniDescriptionDAO;

    public void addUniversity(University un)
    {
        universityDAO.save(un);
    }

    public University getById(long id)
    {
        return universityDAO.findOne(id);
    }

    public List<University> getUniversities(int pageNumber, int unisPerPage)
    {
        Slice<University> slice = universityDAO.findAll(new PageRequest(pageNumber, unisPerPage));
        List<University> result = new ArrayList<>();
        for(University un : slice)
            result.add(un);

        return result;
    }

    public int getPagesCount(int unisPerPage)
    {
        return (int)Math.ceil((double)universityDAO.count() / unisPerPage);
    }

    public University getByIdWithFaculties(int unId)
    {
        University un = getById(unId);
        Hibernate.initialize(un.getFaculties());
        return un;
    }

    public Iterable<University> getUniversitiesByCity(int cityId)
    {
        return universityDAO.findByCity_Id(cityId);
    }

    public void setCityAndAddUniversity(University university, int cityId)
    {
        City city = cityDAO.findOne(cityId);
        university.setCity(city);

        universityDAO.save(university);
    }

    public void removeUniversity(long unId)
    {
        universityDAO.delete(unId);
    }

    public void setImage(long unId, byte[] bytes)
    {
        University university = universityDAO.findOne(unId);
        university.setUnImage(bytes);
    }

    public UniversityDescription getDescription(long unId, String lang)
    {
        return uniDescriptionDAO.findByUniversity_UnIdAndLanguage(unId, lang);
    }
}
