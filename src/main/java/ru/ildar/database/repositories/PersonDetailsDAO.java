package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.PersonDetails;

import java.util.List;

public interface PersonDetailsDAO extends CrudRepository<PersonDetails, String>
{
    List<PersonDetails> findByFirstNameAndLastName(String firstName, String lastName);
    List<PersonDetails> findByTitleAndFaculty(String title, Faculty faculty);
}
