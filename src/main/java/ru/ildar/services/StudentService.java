package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Student;

import java.util.List;

@Service
public interface StudentService
{
    /**
     * Returns an avatar of the student specified by his username
     */
    byte[] getStudentPhoto(String username);

    /**
     * Returns the student specified by his username
     */
    Student getByUsername(String username);

    /**
     * Update student fields firstName, lastName, enrollment, and Email
     * @param details Object which stores new field values
     */
    void updateStudent(Student details);

    /**
     * Sets a group specified by the group ID to the Student instance
     * and saves this instance
     * @param student Student to save
     * @param groupId ID of the group to set to a student
     */
    void setGroupAndAddStudent(Student student, String groupId);

    /**
     * Returns list of students from the group specified by its ID
     */
    List<Student> getByGroup(String groupId);
}
