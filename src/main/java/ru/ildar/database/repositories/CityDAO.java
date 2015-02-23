package ru.ildar.database.repositories;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.City;

/**
 * Repository for performing CRUD operations on the CITIES table
 */
public interface CityDAO extends CrudRepository<City, Integer>, QueryDslPredicateExecutor<City>
{
}
