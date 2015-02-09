package ru.ildar.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class UniversityService
{
    @Autowired
    private UniversityDAO universityDAO;
    @Autowired
    private UniDescriptionDAO uniDescriptionDAO;
    @Autowired
    private PersonService personService;
    @Autowired
    private CityService cityService;
    @Autowired
    private LanguageService languageService;

    /**
     * Add university to the database
     */
    public void addUniversity(University un)
    {
        universityDAO.save(un);
    }

    /**
     * Returns the university specified by its ID
     */
    public University getById(int id)
    {
        return universityDAO.findOne(id);
    }

    /**
     * Returns universities on the specified page
     * @param pageNumber Number of the page
     * @param unisPerPage Amount of universities per page
     */
    public List<University> getUniversities(int pageNumber, int unisPerPage)
    {
        Slice<University> slice = universityDAO.findAll(new PageRequest(pageNumber, unisPerPage));
        List<University> result = new ArrayList<>();
        for(University un : slice)
            result.add(un);

        return result;
    }

    /**
     * Rounding method that returns amount of pages
     */
    public int getPagesCount(int unisPerPage)
    {
        return (int)Math.ceil((double)universityDAO.count() / unisPerPage);
    }

    /**
     * Returns the university specified by its ID with initialized
     * faculties collection
     */
    public University getByIdWithFaculties(int unId)
    {
        University un = getById(unId);
        Hibernate.initialize(un.getFaculties());
        return un;
    }

    /**
     * Returns universities from the city specified by its ID
     */
    public Iterable<University> getUniversitiesByCity(int cityId)
    {
        return universityDAO.findByCity_Id(cityId);
    }

    /**
     * Set the city to the University instance and save it
     * @param university University to save
     * @param cityId ID of the city
     */
    public void setCityAndAddUniversity(University university, int cityId)
    {
        University uni = universityDAO.findByCity_IdAndUnName(cityId, university.getUnName());
        if(uni != null)
            throw new DuplicateKeyException("University with such name and city already exists.");

        City city = cityService.getById(cityId);
        university.setCity(city);

        universityDAO.save(university);
    }

    /**
     * Remove the university from the database
     */
    public void removeUniversity(int unId)
    {
        universityDAO.delete(unId);
    }

    /**
     * Set image to the university
     * @param unId University ID
     * @param bytes Image in bytes
     */
    public void setImage(int unId, byte[] bytes)
    {
        University university = universityDAO.findOne(unId);
        university.setUnImage(bytes);
    }

    /**
     * Returns description of the university on the specified language
     * @param unId University ID
     * @param lang Language
     */
    public UniversityDescription getDescription(int unId, String lang)
    {
        return uniDescriptionDAO.findByUniversity_UnIdAndLanguage(unId, lang);
    }

    /**
     * Saves description of the university
     * @param descr Description to save
     * @param authorName Name of the author of the description text changes
     */
    public void setUniversityDescription(UniversityDescription descr, String authorName)
    {
        Person changeAuthor = personService.getByUserName(authorName);
        descr.setLastChangePerson(changeAuthor);
        uniDescriptionDAO.save(descr);
    }

    public UniversityDescription getDescriptionByLanguageAbbrev(int unId, String langAbbrev)
    {
        Language lang = languageService.getLanguageByAbbreviation(langAbbrev);
        return uniDescriptionDAO.findByUniversity_UnIdAndLanguage(unId, lang.getLanguage());
    }

    public UniversityDescription getFirstDescriptionForUniversity(int unId)
    {
        return uniDescriptionDAO.findOneByUniversity_UnId(unId);
    }

    public void setCity(Integer unId, Integer cityId, String address)
    {
        University university = universityDAO.findOne(unId);
        university.setUnAddress(address);
        City city = cityService.getById(cityId);
        university.setCity(city);
    }
}
