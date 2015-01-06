package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.City;
import ru.ildar.database.repositories.CityDAO;

@Service
public class CityService
{
    @Autowired
    private CityDAO cityDAO;

    public Iterable<City> getAllCities()
    {
        return cityDAO.findAll();
    }
}
