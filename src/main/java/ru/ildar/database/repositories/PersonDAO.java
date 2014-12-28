package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Person;

public interface PersonDAO extends CrudRepository<Person, String>
{

}
