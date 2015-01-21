package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Person;

@Service
public class PictureService
{
    @Autowired
    private PersonService personService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    public byte[] getPicture(String username)
    {
        Person person = personService.getByUserName(username);
        switch (person.getRoleName())
        {
            case "ROLE_TEACHER":
                return teacherService.getTeacherPhoto(username);
            case "ROLE_STUDENT":
                return studentService.getStudentPhoto(username);
            default:
                return null;
        }
    }

    public void setPhoto(byte[] bytes, String name)
    {
        Person person = personService.getByUserName(name);
        switch (person.getRoleName())
        {
            case "ROLE_TEACHER":
                teacherService.getByUserName(name).setPersonPhoto(bytes);
                break;
            case "ROLE_STUDENT":
                studentService.getByUserName(name).setPersonPhoto(bytes);
                break;
        }
    }

    public void removeAvatar(String username)
    {
        setPhoto(null, username);
    }
}
