package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Group;
import ru.ildar.database.entities.Student;
import ru.ildar.database.repositories.GroupDAO;
import ru.ildar.database.repositories.StudentDAO;

import java.util.List;

@Service
public class StudentService
{
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private GroupDAO groupDAO;

    public byte[] getStudentPhoto(String username)
    {
        return studentDAO.findOne(username).getPersonPhoto();
    }

    public Student getByUsername(String username)
    {
        return studentDAO.findOne(username);
    }

    public void setGroupAndPhotoAndUpdate(Student details)
    {
        Student stud = studentDAO.findOne(details.getUsername());
        stud.setFirstName(details.getFirstName());
        stud.setLastName(details.getLastName());
        stud.setEnrollment(details.getEnrollment());
        stud.setEmail(details.getEmail());
    }

    public Student getByUserName(String username)
    {
        return studentDAO.findOne(username);
    }

    public void setGroupAndAddStudent(Student student, String groupId)
    {
        Group group = groupDAO.findOne(groupId);
        student.setGroup(group);
        studentDAO.save(student);
    }

    public List<Student> getByGroup(String groupId)
    {
        return studentDAO.getByGroup_GroupId(groupId);
    }
}
