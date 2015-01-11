package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.controllers.teacher.StudentGradePojo;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Student;
import ru.ildar.database.entities.Teacher;
import ru.ildar.database.entities.TeachersGroups;
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

    public List<Grade> getStudentGradesInSemester(String studUsername, long semester)
    {
        return gradeDAO.findByStudent_UsernameAndSemester(studUsername, semester);
    }

    public void addGrade(Grade grade)
    {
        gradeDAO.save(grade);
    }

    public Set<Long> getStudentSemesters(String studUsername)
    {
        List<Grade> grades = gradeDAO.findByStudent_Username(studUsername);
        Set<Long> results = new TreeSet<>();
        grades.stream().map(Grade::getSemester).forEach(results::add);

        return results;
    }

    public List<Grade> getByTeachersGroups(TeachersGroups tGroups)
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

    public Grade getStudentGrade(String subject, int semester, String username, String teacher)
    {
        return gradeDAO.findOneBySubjectNameAndSemesterAndStudent_UsernameAndTeacher_Username(subject, semester, username, teacher);
    }

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
}
