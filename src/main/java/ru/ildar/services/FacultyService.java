package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Faculty;

@Service
public interface FacultyService
{
    /**
     * Saves or updates faculty in the database
     */
    void saveOrUpdateFaculty(Faculty fac);

    /**
     * Returns faculties of the university, specified by the ID
     */
    Iterable<Faculty> getFacultiesByUniversity(int universityId);

    /**
     * Remove a faculty by its ID
     */
    void removeFaculty(int facultyId);

    /**
     * Returns count of students in this university - sum of counts of all students
     * in the faculties of this university
     * @param unId ID of the university whose count of students to return
     */
    int getStudentsCount(int unId);

    /**
     * Returns the faculty specified by its ID
     */
    Faculty get(int facultyId);
}
