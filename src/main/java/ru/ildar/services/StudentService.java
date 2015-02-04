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
    private GroupService groupService;

    /**
     * Returns an avatar of the student specified by his username
     */
    public byte[] getStudentPhoto(String username)
    {
        return studentDAO.findOne(username).getPersonPhoto();
    }

    /**
     * Returns the student specified by his username
     */
    public Student getByUsername(String username)
    {
        return studentDAO.findOne(username);
    }

    /**
     * Update student fields firstName, lastName, enrollment, and Email
     * @param details Object which stores new field values
     */
    public void updateStudent(Student details)
    {
        Student stud = studentDAO.findOne(details.getUsername());
        stud.setFirstName(details.getFirstName());
        stud.setLastName(details.getLastName());
        stud.setEnrollment(details.getEnrollment());
        stud.setEmail(details.getEmail());
    }

    /**
     * Sets a group specified by the group ID to the Student instance
     * and saves this instance
     * @param student Student to save
     * @param groupId ID of the group to set to a student
     */
    public void setGroupAndAddStudent(Student student, String groupId)
    {
        Group group = groupService.getGroupById(groupId);
        student.setGroup(group);
        studentDAO.save(student);
    }

    /**
     * Returns list of students from the group specified by its ID
     */
    public List<Student> getByGroup(String groupId)
    {
        return studentDAO.getByGroup_GroupId(groupId);
    }
}
