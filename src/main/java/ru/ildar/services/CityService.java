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

    public City getById(int cityId)
    {
        return cityDAO.findOne(cityId);
    }

    public void addCity(City city)
    {
        cityDAO.save(city);
    }

    public void setCityAndLanguageAndAddCityLocalization(Integer cityId,
                                                         String langAbbrev, LocalizedCity cityLoc)
    {
        City city = cityDAO.findOne(cityId);
        Language lang = languageService.getLanguageByAbbreviation(langAbbrev);
        cityLoc.setCity(city);
        cityLoc.setLanguage(lang);
        localizedCityDAO.save(cityLoc);
    }

    public LocalizedCity getLocalization(int cityId, String langAbbrev)
    {
        return localizedCityDAO.findByCity_IdAndLanguage_Abbreviation(cityId, langAbbrev);
    }

    public void removeCity(int cityId)
    {
        cityDAO.delete(cityId);
    }

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
}
