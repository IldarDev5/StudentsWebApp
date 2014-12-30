package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.PersonDetails;
import ru.ildar.database.repositories.PersonDAO;
import ru.ildar.database.repositories.PersonDetailsDAO;

import java.util.List;

@Service
public class PersonService
{
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private PersonDetailsDAO personDetailsDAO;

    public void addPerson(Person person)
    {
        personDAO.save(person);
    }

    public Person getByUserName(String username)
    {
        return personDAO.findOne(username);
    }

    public void removePerson(String username)
    {
        personDAO.delete(username);
    }

    public void addPersonDetails(PersonDetails p)
    {
        personDetailsDAO.save(p);
    }

    public List<PersonDetails> getPeopleByName(String firstName, String lastName)
    {
        return personDetailsDAO.findByFirstNameAndLastName(firstName, lastName);
    }

    public List<PersonDetails> getPeopleByTitleAndFaculty(String title, Faculty faculty)
    {
        return personDetailsDAO.findByTitleAndFaculty(title, faculty);
    }

    public void savePersonDetails(PersonDetails details)
    {
        personDetailsDAO.save(details);
    }
}
