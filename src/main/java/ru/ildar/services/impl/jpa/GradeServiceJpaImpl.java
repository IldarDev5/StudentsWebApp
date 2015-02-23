package ru.ildar.services.impl.jpa;

import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.GradeDAO;
import ru.ildar.services.GradeService;
import ru.ildar.services.factory.impl.JpaServiceFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class GradeServiceJpaImpl implements GradeService
{
    @Autowired
    private GradeDAO gradeDAO;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public List<Grade> getStudentGradesInSemester(String studUsername, long semester)
    {
        QGrade grade = QGrade.grade;
        BooleanExpression expr = grade.student.username.eq(studUsername)
                .and(grade.semester.eq(semester));
        Iterable<Grade> iter = gradeDAO.findAll(expr);

        List<Grade> result = new ArrayList<>();
        iter.forEach(result::add);
        return result;
    }

    @Override
    public void addGrade(Grade grade)
    {
        gradeDAO.save(grade);
    }

    @Override
    public Set<Long> getStudentSemesters(String studUsername)
    {
        BooleanExpression expr = QGrade.grade.student.username.eq(studUsername);
        Iterable<Grade> grades = gradeDAO.findAll(expr);
        Set<Long> results = new TreeSet<>();
        for(Grade grade : grades)
            results.add(grade.getSemester());

        return results;
    }

    @Override
    public List<Grade> getGradesByTeachersGroups(TeachersGroups tGroups)
    {
        String groupId = tGroups.getGroup().getGroupId();
        String subject = tGroups.getSubjectName();
        long semester = tGroups.getSemester();

        QGrade grade = QGrade.grade;
        BooleanExpression expr = grade.subjectName.eq(subject)
                .and(grade.semester.eq(semester))
                .and(grade.student.group.groupId.eq(groupId));
        Iterable<Grade> iter = gradeDAO.findAll(expr,
                grade.student.username.asc(), grade.semester.asc());

        List<Grade> grades = new ArrayList<>();
        iter.forEach(grades::add);
        return grades;
    }

    @Override
    public Grade getStudentGrade(String subject, long semester, String username, String teacher)
    {
        QGrade grade = QGrade.grade;
        BooleanExpression expr = grade.subjectName.eq(subject)
                .and(grade.semester.eq(semester))
                .and(grade.student.username.eq(username))
                .and(grade.teacher.username.eq(teacher));
        return gradeDAO.findOne(expr);
    }

    @Override
    public void setStudentAndTeacherAndAddGrade(String studStr, String teacherStr, Grade grade)
    {
        Grade prevGrade = getStudentGrade(grade.getSubjectName(), grade.getSemester(), studStr, teacherStr);

        if(prevGrade != null)
        {
            prevGrade.setGradeValue(grade.getGradeValue());
            return;
        }

        Student student = serviceFactory.getStudentService().getByUsername(studStr);
        Teacher teacher = serviceFactory.getTeacherService().getByUserName(teacherStr);
        grade.setStudent(student);
        grade.setTeacher(teacher);

        gradeDAO.save(grade);
    }

    @Override
    public void removeGrade(int gradeId)
    {
        gradeDAO.delete(gradeId);
    }

    @Override
    public void setTranslationToGradeSubjects(List<Grade> grades, String languageAbbrev)
    {
        grades.stream().forEach((grade) ->
        {
            LocalizedSubject locSubj = serviceFactory.getSubjectService()
                    .getSubjectLocalization(grade.getSubjectName(), languageAbbrev);
            if(locSubj != null)
                grade.setTranslation(locSubj.getSubjectTranslation());
        });
    }
}
