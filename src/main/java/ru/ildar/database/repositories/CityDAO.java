package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.City;

public interface CityDAO extends CrudRepository<City, Integer>
{
}
