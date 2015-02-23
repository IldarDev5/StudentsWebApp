package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Group;
import ru.ildar.database.entities.QStudent;
import ru.ildar.database.entities.Student;
import ru.ildar.database.repositories.StudentDAO;
import ru.ildar.services.StudentService;
import ru.ildar.services.factory.impl.JpaServiceFactory;

import java.util.ArrayList;
import java.util.List;

import static ru.ildar.CollectionsUtil.getListFromIterable;

@Service
public class StudentServiceJpaImpl implements StudentService
{
    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public byte[] getStudentPhoto(String username)
    {
        return studentDAO.findOne(username).getPersonPhoto();
    }

    @Override
    public Student getByUsername(String username)
    {
        return studentDAO.findOne(username);
    }

    @Override
    public void updateStudent(Student details)
    {
        Student stud = studentDAO.findOne(details.getUsername());
        stud.setFirstName(details.getFirstName());
        stud.setLastName(details.getLastName());
        stud.setEnrollment(details.getEnrollment());
        stud.setEmail(details.getEmail());
    }

    @Override
    public void setGroupAndAddStudent(Student student, String groupId)
    {
        Group group = serviceFactory.getGroupService().getGroupById(groupId);
        student.setGroup(group);
        studentDAO.save(student);
    }

    @Override
    public List<Student> getByGroup(String groupId)
    {
        return getListFromIterable(studentDAO.findAll(QStudent.student.group.groupId.eq(groupId)));
    }
}
