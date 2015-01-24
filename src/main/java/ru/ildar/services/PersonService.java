package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.*;


@Service
public class PersonService
{
    @Autowired
    private PersonDAO personDAO;

    /**
     * Add a person to the database
     */
    public void addPerson(Person person)
    {
        personDAO.save(person);
    }

    /**
     * Returns the person specified by his username
     */
    public Person getByUserName(String username)
    {
        return personDAO.findOne(username);
    }

    /**
     * Remove the person specified by his username
     */
    public void removePerson(String username)
    {
        personDAO.delete(username);
    }
}
