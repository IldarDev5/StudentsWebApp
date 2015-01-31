package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.UniversityDescription;

/**
 * Repository for performing CRUD operations on the UN_DESCRIPTION table
 */
public interface UniDescriptionDAO extends CrudRepository<UniversityDescription, Integer>
{
    UniversityDescription findByUniversity_UnIdAndLanguage(int unId, String lang);
    UniversityDescription findOneByUniversity_UnId(int unId);
}
