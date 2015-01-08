package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.database.repositories.GradeDAO;
import ru.ildar.database.repositories.PersonDAO;


@Service
public class PersonService
{
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private FacultyDAO facultyDAO;
    @Autowired
    private GradeDAO gradeDAO;

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
}
