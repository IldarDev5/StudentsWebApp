package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.Language;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.database.repositories.CityDAO;
import ru.ildar.database.repositories.LocalizedCityDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CityService
{
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private LocalizedCityDAO localizedCityDAO;
    @Autowired
    private LanguageService languageService;

    /**
     * Get all cities from the database
     */
    public List<City> getAllCities()
    {
        Iterable<City> cities = cityDAO.findAll();
        List<City> result = new ArrayList<>();
        cities.forEach(result::add);
        return result;
    }

    /**
     * Get city by its ID
     */
    public City getById(int cityId)
    {
        return cityDAO.findOne(cityId);
    }

    /**
     * Add city to the database
     */
    public void addCity(City city)
    {
        cityDAO.save(city);
    }

    /**
     * Add city localization to the database, but before set city specified by its ID
     * and language specified by its abbreviation to this localization
     * @param cityId ID of the city
     * @param langAbbrev Abbreviation of the language
     * @param cityLoc City localization object to save
     */
    public void setCityAndLanguageAndAddCityLocalization(Integer cityId,
                                                         String langAbbrev, LocalizedCity cityLoc)
    {
        City city = cityDAO.findOne(cityId);
        Language lang = languageService.getLanguageByAbbreviation(langAbbrev);
        cityLoc.setCity(city);
        cityLoc.setLanguage(lang);
        localizedCityDAO.save(cityLoc);
    }

    /**
     * Returns city localization
     * @param cityId ID of the city whose localization to return
     * @param langAbbrev Abbreviation of the language of which localization must be returned
     */
    public LocalizedCity getLocalization(int cityId, String langAbbrev)
    {
        return localizedCityDAO.findByCity_IdAndLanguage_Abbreviation(cityId, langAbbrev);
    }

    /**
     * Remove the city specified by its ID
     */
    public void removeCity(int cityId)
    {
        cityDAO.delete(cityId);
    }

    /**
     * Get localization of all cities of the specific language
     * @param languageAbbrev Abbreviation of the language whose localizations to return
     */
    public List<LocalizedCity> getCitiesLocalizations(String languageAbbrev)
    {
        Iterable<City> cities = cityDAO.findAll();
        List<LocalizedCity> citiesLocs = new ArrayList<>();
        cities.forEach((city) ->
        {
            LocalizedCity cityLoc = localizedCityDAO.findByCity_IdAndLanguage_Abbreviation
                    (city.getId(), languageAbbrev);
            if(cityLoc != null)
                citiesLocs.add(cityLoc);
            else
            //If there's no city localization for such locale, add default locale - English
            {
                cityLoc = localizedCityDAO.findByCity_IdAndLanguage_Abbreviation
                        (city.getId(), Locale.US.getLanguage());
                citiesLocs.add(cityLoc);
            }
        });
        return citiesLocs;
    }

    /**
     * Deletes city localization from the database and returns true if
     * localization was successfully deleted; otherwise returns false.
     * Localization can be deleted only if it's not a default localization(English)
     * @param locCityId ID of the city localization object
     */
    public boolean removeCityLocalization(Integer locCityId)
    {
        LocalizedCity locCity = localizedCityDAO.findOne(locCityId);
        if(locCity.getLanguage().getAbbreviation().equals(Locale.US.getLanguage()))
        {
            return false;
        }
        else
        {
            localizedCityDAO.delete(locCity);
            return true;
        }
    }
}
