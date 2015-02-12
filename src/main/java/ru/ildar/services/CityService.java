package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.LocalizedCity;

import java.util.List;

@Service
public interface CityService
{
    /**
     * Get all cities from the database
     */
    List<City> getAllCities();

    /**
     * Get city by its ID
     */
    City getById(int cityId);

    /**
     * Add city to the database
     */
    void addCity(City city);

    /**
     * Add city localization to the database, but before set city specified by its ID
     * and language specified by its abbreviation to this localization
     * @param cityId ID of the city
     * @param langAbbrev Abbreviation of the language
     * @param cityLoc City localization object to save
     */
    void setCityAndLanguageAndAddCityLocalization(Integer cityId,
                                                         String langAbbrev, LocalizedCity cityLoc);

    /**
     * Returns city localization
     * @param cityId ID of the city whose localization to return
     * @param langAbbrev Abbreviation of the language of which localization must be returned
     */
    LocalizedCity getLocalization(int cityId, String langAbbrev);

    /**
     * Remove the city specified by its ID
     */
    void removeCity(int cityId);

    /**
     * Get localization of all cities of the specific language
     * @param languageAbbrev Abbreviation of the language whose localizations to return
     */
    List<LocalizedCity> getCitiesLocalizations(String languageAbbrev);

    /**
     * Deletes city localization from the database and returns true if
     * localization was successfully deleted or if <code>locCityId</code>
     * is null; otherwise returns false.
     * Localization can be deleted only if it's not a default localization(English)
     * @param locCityId ID of the city localization object
     */
    boolean removeCityLocalization(Integer locCityId);
}
