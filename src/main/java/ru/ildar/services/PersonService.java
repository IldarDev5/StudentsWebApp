package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Person;

@Service
public interface PersonService
{
    /**
     * Add a person to the database
     */
    void addPerson(Person person);

    /**
     * Returns the person specified by his username
     */
    Person getByUserName(String username);

    /**
     * Remove the person specified by his username
     */
    void removePerson(String username);
}
