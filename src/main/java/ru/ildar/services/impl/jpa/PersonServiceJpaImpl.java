package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.PersonDAO;
import ru.ildar.services.PersonService;

@Service
public class PersonServiceJpaImpl implements PersonService
{
    @Autowired
    private PersonDAO personDAO;

    @Override
    public void addPerson(Person person)
    {
        personDAO.save(person);
    }

    @Override
    public Person getByUserName(String username)
    {
        return personDAO.findOne(username);
    }

    @Override
    public void removePerson(String username)
    {
        personDAO.delete(username);
    }
}
