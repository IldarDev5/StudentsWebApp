package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.GradeDAO;
import ru.ildar.services.GradeService;
import ru.ildar.services.factory.impl.JpaServiceFactory;

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
        return gradeDAO.findByStudent_UsernameAndSemester(studUsername, semester);
    }

    @Override
    public void addGrade(Grade grade)
    {
        gradeDAO.save(grade);
    }

    @Override
    public Set<Long> getStudentSemesters(String studUsername)
    {
        List<Grade> grades = gradeDAO.findByStudent_Username(studUsername);
        Set<Long> results = new TreeSet<>();
        grades.stream().map(Grade::getSemester).forEach(results::add);

        return results;
    }

    @Override
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

    @Override
    public Grade getStudentGrade(String subject, int semester, String username, String teacher)
    {
        return gradeDAO.findOneBySubjectNameAndSemesterAndStudent_UsernameAndTeacher_Username(subject, semester, username, teacher);
    }

    @Override
    public void setStudentAndTeacherAndAddGrade(String studStr, String teacherStr, Grade grade)
    {
        Grade prevGrade = gradeDAO.findOneBySubjectNameAndSemesterAndStudent_UsernameAndTeacher_Username(grade.getSubjectName(), grade.getSemester(), studStr, teacherStr);

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
