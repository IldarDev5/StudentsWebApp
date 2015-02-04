package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.City;
import ru.ildar.database.repositories.CityDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService
{
    @Autowired
    private CityDAO cityDAO;

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
}
