package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Group;

/**
 * Repository for performing CRUD operations on the GROUPS table
 */
public interface GroupDAO extends CrudRepository<Group, String>
{
    Iterable<Group> findByFaculty_FacultyId(long facultyId);
}
