package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;

import java.util.List;

@Service
public interface UniversityService
{
    /**
     * Add university to the database
     */
    void addUniversity(University un);

    /**
     * Returns the university specified by its ID
     */
    University getById(int id);

    /**
     * Returns universities on the specified page
     * @param pageNumber Number of the page
     * @param unisPerPage Amount of universities per page
     */
    List<University> getUniversities(int pageNumber, int unisPerPage);

    /**
     * Rounding method that returns amount of pages
     */
    int getPagesCount(int unisPerPage);

    /**
     * Returns the university specified by its ID with initialized
     * faculties collection
     */
    University getByIdWithFaculties(int unId);

    /**
     * Returns universities from the city specified by its ID
     */
    Iterable<University> getUniversitiesByCity(int cityId);

    /**
     * Set the city to the University instance and save it
     * @param university University to save
     * @param cityId ID of the city
     */
    void setCityAndAddUniversity(University university, int cityId);

    /**
     * Remove the university from the database
     */
    void removeUniversity(int unId);

    /**
     * Set image to the university
     * @param unId University ID
     * @param bytes Image in bytes
     */
    void setImage(int unId, byte[] bytes);

    /**
     * Returns description of the university on the specified language
     * @param unId University ID
     * @param lang Language
     */
    UniversityDescription getDescription(int unId, String lang);

    /**
     * Saves description of the university
     * @param descr Description to save
     * @param authorName Name of the author of the description text changes
     */
    void setUniversityDescription(UniversityDescription descr, String authorName);

    /**
     * Returns university description with language specified by the language abbreviation
     * @param unId ID of the university
     * @param langAbbrev Language abbreviation
     */
    UniversityDescription getDescriptionByLanguageAbbrev(int unId, String langAbbrev);

    /**
     * Returns first non-null description for this university
     * @param unId ID of the university
     */
    UniversityDescription getFirstDescriptionForUniversity(int unId);

    /**
     * Sets city and address to the university
     * @param unId ID of the university
     * @param cityId ID of the city to set
     * @param address Address of the university
     */
    void setCity(Integer unId, Integer cityId, String address);
}
