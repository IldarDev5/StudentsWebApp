package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;

import java.util.*;

@Service
public interface GradeService
{
    /**
     * Returns grades of the specified student in the specified semester
     */
    List<Grade> getStudentGradesInSemester(String studUsername, long semester);

    /**
     * Add grade to the database
     */
    void addGrade(Grade grade);

    /**
     * Returns semesters numbers in which this student got some grades
     */
    Set<Long> getStudentSemesters(String studUsername);

    /**
     * Returns grades, the criteria of search of which is composed of three
     * fields of TeachersGroups instance - groupId, subject and semester.
     */
    List<Grade> getGradesByTeachersGroups(TeachersGroups tGroups);

    /**
     * Find grade by the specified fields values.
     * @param subject Subject on which grade was set
     * @param semester Number of the semester in which grade was set
     * @param username Username of the student who got this grade
     * @param teacher Teacher who set this grade
     */
    Grade getStudentGrade(String subject, int semester, String username, String teacher);

    /**
     * Set Student and Teacher instances, found by their specified usernames, to the grade
     * fields student and teacher, then save this grade
     * @param studStr Student username
     * @param teacherStr Teacher username
     * @param grade Grade instance to save
     */
    void setStudentAndTeacherAndAddGrade(String studStr, String teacherStr, Grade grade);

    /**
     * Remove the grade from the database
     */
    void removeGrade(int gradeId);

    void setTranslationToGradeSubjects(List<Grade> grades, String languageAbbrev);
}
