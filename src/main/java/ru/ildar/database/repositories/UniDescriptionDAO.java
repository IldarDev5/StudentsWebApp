package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.UniversityDescription;

public interface UniDescriptionDAO extends CrudRepository<UniDescriptionDAO, Long>
{
    UniversityDescription findByUniversity_UnIdAndLanguage(long unId, String lang);
}
