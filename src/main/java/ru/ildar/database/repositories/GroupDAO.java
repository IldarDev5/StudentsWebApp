package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Group;

/**
 * Created by Ildar on 07.01.15.
 */
public interface GroupDAO extends CrudRepository<Group, String>
{
    Iterable<Group> findByFaculty_FacultyId(long facultyId);
}
