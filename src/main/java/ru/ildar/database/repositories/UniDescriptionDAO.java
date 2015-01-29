package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.UniversityDescription;

/**
 * Repository for performing CRUD operations on the UN_DESCRIPTION table
 */
public interface UniDescriptionDAO extends CrudRepository<UniversityDescription, Long>
{
    UniversityDescription findByUniversity_UnIdAndLanguage(long unId, String lang);
    UniversityDescription findOneByUniversity_UnId(Long unId);
}
