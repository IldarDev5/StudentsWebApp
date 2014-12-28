package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Faculty;

public interface FacultyDAO extends CrudRepository<Faculty, Long>
{

}
