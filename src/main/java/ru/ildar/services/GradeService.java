package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.GradeDAO;

import java.util.*;

@Service
public class GradeService
{
    @Autowired
    private GradeDAO gradeDAO;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private SubjectService subjectService;

    /**
     * Returns grades of the specified student in the specified semester
     */
    public List<Grade> getStudentGradesInSemester(String studUsername, long semester)
    {
        return gradeDAO.findByStudent_UsernameAndSemester(studUsername, semester);
    }

    /**
     * Add grade to the database
     */
    public void addGrade(Grade grade)
    {
        gradeDAO.save(grade);
    }

    /**
     * Returns semesters numbers in which this student got some grades
     */
    public Set<Long> getStudentSemesters(String studUsername)
    {
        List<Grade> grades = gradeDAO.findByStudent_Username(studUsername);
        Set<Long> results = new TreeSet<>();
        grades.stream().map(Grade::getSemester).forEach(results::add);

        return results;
    }

    /**
     * Returns grades, the criteria of search of which is composed of three
     * fields of TeachersGroups instance - groupId, subject and semester.
     */
    public List<Grade> getGradesByTeachersGroups(TeachersGroups tGroups)
    {
        String groupId = tGroups.getGroup().getGroupId();
        String subject = tGroups.getSubjectName();
        long semester = tGroups.getSemester();

        Sort sort = new Sort(Sort.Direction.ASC, "student_Username");
        sort = sort.and(new Sort(Sort.Direction.ASC, "semester"));

        List<Grade> grades =
                gradeDAO.findBySubjectNameAndSemesterAndStudent_Group_GroupId(subject, semester, groupId, sort);
        return grades;
    }

    /**
     * Find grade by the specified fields values.
     * @param subject Subject on which grade was set
     * @param semester Number of the semester in which grade was set
     * @param username Username of the student who got this grade
     * @param teacher Teacher who set this grade
     */
    public Grade getStudentGrade(String subject, int semester, String username, String teacher)
    {
        return gradeDAO.findOneBySubjectNameAndSemesterAndStudent_UsernameAndTeacher_Username(subject, semester, username, teacher);
    }

    /**
     * Set Student and Teacher instances, found by their specified usernames, to the grade
     * fields student and teacher, then save this grade
     * @param studStr Student username
     * @param teacherStr Teacher username
     * @param grade Grade instance to save
     */
    public void setStudentAndTeacherAndAddGrade(String studStr, String teacherStr, Grade grade)
    {
        Grade prevGrade = gradeDAO.findOneBySubjectNameAndSemesterAndStudent_UsernameAndTeacher_Username(grade.getSubjectName(), grade.getSemester(), studStr, teacherStr);
        
        if(prevGrade != null)
        {
            prevGrade.setGradeValue(grade.getGradeValue());
            return;
        }

        Student student = studentService.getByUsername(studStr);
        Teacher teacher = teacherService.getByUserName(teacherStr);
        grade.setStudent(student);
        grade.setTeacher(teacher);

        gradeDAO.save(grade);
    }

    /**
     * Remove the grade from the database
     */
    public void removeGrade(int gradeId)
    {
        gradeDAO.delete(gradeId);
    }

    public void setTranslationToGradeSubjects(List<Grade> grades, String languageAbbrev)
    {
        grades.stream().forEach((grade) ->
        {
            LocalizedSubject locSubj = subjectService
                    .getSubjectLocalization(grade.getSubjectName(), languageAbbrev);
            if(locSubj != null)
                grade.setTranslation(locSubj.getSubjectTranslation());
        });
    }
}
