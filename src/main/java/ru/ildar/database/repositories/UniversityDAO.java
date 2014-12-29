package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.University;

public interface UniversityDAO extends CrudRepository<University, Long>
{
    University findByUnName(String unName);
}
