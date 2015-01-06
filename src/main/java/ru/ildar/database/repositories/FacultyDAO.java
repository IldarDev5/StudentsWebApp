package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.University;

public interface FacultyDAO extends CrudRepository<Faculty, Long>
{
    Iterable<Faculty> findByUniversity_UnId(long universityId);
}
