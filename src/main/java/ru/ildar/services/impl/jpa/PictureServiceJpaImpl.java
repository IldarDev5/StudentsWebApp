package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Person;
import ru.ildar.services.PersonService;
import ru.ildar.services.PictureService;
import ru.ildar.services.factory.impl.JpaServiceFactory;

@Service
public class PictureServiceJpaImpl implements PictureService
{
    @Autowired
    private PersonService personService;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public byte[] getPicture(String username)
    {
        Person person = personService.getByUserName(username);
        switch (person.getRoleName())
        {
            case "ROLE_TEACHER":
                return serviceFactory.getTeacherService().getTeacherPhoto(username);
            case "ROLE_STUDENT":
                return serviceFactory.getStudentService().getStudentPhoto(username);
            default:
                return null;
        }
    }

    @Override
    public void setPhoto(byte[] bytes, String name)
    {
        Person person = personService.getByUserName(name);
        switch (person.getRoleName())
        {
            case "ROLE_TEACHER":
                serviceFactory.getTeacherService().getByUserName(name).setPersonPhoto(bytes);
                break;
            case "ROLE_STUDENT":
                serviceFactory.getStudentService().getByUsername(name).setPersonPhoto(bytes);
                break;
        }
    }

    @Override
    public void removeAvatar(String username)
    {
        setPhoto(null, username);
    }
}
