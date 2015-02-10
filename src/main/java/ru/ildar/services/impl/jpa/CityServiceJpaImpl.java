package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.Language;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.database.repositories.CityDAO;
import ru.ildar.database.repositories.LocalizedCityDAO;
import ru.ildar.services.CityService;
import ru.ildar.services.LanguageService;
import ru.ildar.services.factory.ServiceFactory;
import ru.ildar.services.factory.impl.JpaServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CityServiceJpaImpl implements CityService
{
    @Autowired
    private CityDAO cityDAO;
    @Autowired
    private LocalizedCityDAO localizedCityDAO;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public List<City> getAllCities()
    {
        Iterable<City> cities = cityDAO.findAll();
        List<City> result = new ArrayList<>();
        cities.forEach(result::add);
        return result;
    }

    @Override
    public City getById(int cityId)
    {
        return cityDAO.findOne(cityId);
    }

    @Override
    public void addCity(City city)
    {
        cityDAO.save(city);
    }

    @Override
    public void setCityAndLanguageAndAddCityLocalization(Integer cityId,
                                                         String langAbbrev, LocalizedCity cityLoc)
    {
        City city = cityDAO.findOne(cityId);
        Language lang = serviceFactory.getLanguageService().getLanguageByAbbreviation(langAbbrev);
        cityLoc.setCity(city);
        cityLoc.setLanguage(lang);
        localizedCityDAO.save(cityLoc);
    }

    @Override
    public LocalizedCity getLocalization(int cityId, String langAbbrev)
    {
        return localizedCityDAO.findByCity_IdAndLanguage_Abbreviation(cityId, langAbbrev);
    }

    @Override
    public void removeCity(int cityId)
    {
        cityDAO.delete(cityId);
    }

    @Override
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

    @Override
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
