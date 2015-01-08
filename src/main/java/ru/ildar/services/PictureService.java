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
        if(person.getRoleName().equals("ROLE_TEACHER"))
            return teacherService.getTeacherPhoto(username);
        else if(person.getRoleName().equals("ROLE_STUDENT"))
            return studentService.getStudentPhoto(username);
        else
            return null;
    }
}
