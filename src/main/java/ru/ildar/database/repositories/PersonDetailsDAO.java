package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.PersonDetails;

public interface PersonDetailsDAO extends CrudRepository<PersonDetails, String>
{

}
