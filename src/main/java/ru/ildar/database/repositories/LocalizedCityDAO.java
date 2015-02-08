package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.LocalizedCity;

public interface LocalizedCityDAO extends CrudRepository<LocalizedCity, Integer>
{
    LocalizedCity findByCity_IdAndLanguage_Abbreviation(int cityId, String lang);
}
