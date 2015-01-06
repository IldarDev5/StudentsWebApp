package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.PersonDetails;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.database.repositories.GradeDAO;
import ru.ildar.database.repositories.PersonDAO;
import ru.ildar.database.repositories.PersonDetailsDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class PersonService
{
    @Autowired
    private PersonDAO personDAO;
    @Autowired
    private PersonDetailsDAO personDetailsDAO;
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

    public void saveOrUpdatePersonDetails(PersonDetails details)
    {
        personDetailsDAO.save(details);
    }

    public void setFacultyAndUpdate(PersonDetails details, long facultyId)
    {
        Faculty fac = facultyDAO.findOne(facultyId);
        details.setFaculty(fac);
        personDetailsDAO.save(details);
    }

    public void setImage(String username, byte[] image)
    {
        PersonDetails person = personDetailsDAO.findOne(username);
        person.setPersonPhoto(image);
    }

    public List<Person> getTeachers(int pageNumber, int teachersPerPage)
    {
        Iterable<Person> it = personDAO.findByRoleName("ROLE_TEACHER",
                new PageRequest(pageNumber, teachersPerPage));
        List<Person> result = new ArrayList<>();
        it.forEach(result::add);
        return result;
    }

    public int getTeachersPagesCount(int teachersPerPage)
    {
        return (int)Math.ceil((double)personDAO.countByRoleName("ROLE_TEACHER") / teachersPerPage);
    }

    public Set<Person> getTeachersBySubject(String subjectName)
    {
        List<Grade> grades = gradeDAO.findBySubjectName(subjectName);
        Set<Person> result = new TreeSet<>();
        grades.forEach((g) -> result.add(g.getTeacher()));
        return result;
    }
}
