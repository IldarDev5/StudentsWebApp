package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;

import java.util.*;

@Service
public interface TeacherService
{
    /**
     * Returns teachers of the specified subject.
     * The method searches, whether any of the teachers teaches
     * this subject some group, and collects all these teachers
     */
    Set<Teacher> getTeachersBySubject(String subjectName);

    /**
     * Rounding method for amount of pages
     */
    int getTeachersPagesCount(int teachersPerPage);

    /**
     * Returns teachers of the specified student and subjects that each teacher
     * teaches him. Teacher of the student = teacher of the group this student is in.
     */
    Map<Teacher, Set<String>> getStudentTeachers(String studName, Locale locale);

    /**
     * Returns teachers on the specified page
     * @param pageNumber Number of the page
     * @param teachersPerPage Amount of teachers per paga
     */
    List<Teacher> getTeachers(int pageNumber, int teachersPerPage);

    /**
     * Returns a photo of the teacher specified by his username
     */
    byte[] getTeacherPhoto(String username);

    /**
     * Returns the teacher specified by his username
     */
    Teacher getByUserName(String username);

    /**
     * Assigns the photo and the university to the teacher instance
     * and updates it
     * @param details Teacher instance the values of which would be used for updating
     * @param unId ID of the university
     */
    void setUniversityAndPhotoAndUpdate(Teacher details, int unId);

    /**
     * Sets the university to the teacher and updates it
     * @param teacher Teacher instance the values of which would be used for updating
     * @param uniId ID of the university
     */
    void setUniversityAndAddTeacher(Teacher teacher, int uniId);

    /**
     * Get groups that this teachers teaches, no matter what subjects
     */
    List<TeachersGroups> getTeachersGroups(String name);

    /**
     * Returns the TeachersGroups instance specified by its ID
     */
    TeachersGroups getTeachersGroupsById(int id);

    /**
     * Get TeachersGroups instances specified by three fields - subject, name, and student username
     */
    TeachersGroups getTeachersGroupsBySubjectSemesterAndGroupStudent(String subject, int semester,
                                                 String studentSelect, String teacherUsername);

    /**
     * Add TeachersGroups instance to the database, but before
     * set group and teacher to it
     * @param tg Instance to save
     * @param groupId ID of the group
     * @param teacherUsername Username of the teacher
     */
    void setGroupAndTeacherAndAddTeachersGroups(TeachersGroups tg,
                                                       String groupId, String teacherUsername);

    /**
     * Get all teachers of the university specified by its ID
     */
    List<Teacher> getTeachersByUniversity(int uniId);

    /** Remove TeachersGroups instance from the database by its ID */
    void removeTeachersGroups(Integer tGroupId);

    /**
     * Sets localization based on the language parameter to the subjects of TeachersGroups instances
     * @param tGroups TeachersGroups instances that have subjectTranslation field that has to be
     *                filled
     * @param languageAbbrev Abbreviation of the language whose localization to set to tGroups
     */
    void setTranslationToSubjects(List<TeachersGroups> tGroups, String languageAbbrev);
}
