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

    /**
     * Returns an avatar of the specified user, whether it's a
     * teacher or a student.
     */
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

    /**
     * Set the avatar specified by the array of bytes to the
     * person specified by his username, whether it's a teacher or
     * a student
     * @param bytes An avatar image
     * @param name Username of a person
     */
    public void setPhoto(byte[] bytes, String name)
    {
        Person person = personService.getByUserName(name);
        switch (person.getRoleName())
        {
            case "ROLE_TEACHER":
                teacherService.getByUserName(name).setPersonPhoto(bytes);
                break;
            case "ROLE_STUDENT":
                studentService.getByUsername(name).setPersonPhoto(bytes);
                break;
        }
    }

    /**
     * Remove the avatar from a user
     * @param username Username of the user
     */
    public void removeAvatar(String username)
    {
        setPhoto(null, username);
    }
}
