package ru.ildar.database.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Person;

import java.util.List;

/**
 * Repository for performing CRUD operations on the GRADES table
 */
public interface GradeDAO extends CrudRepository<Grade, Integer>
{
    List<Grade> findByStudent_UsernameAndSemester(String studUsername, long semester);
    List<Grade> findByStudent_Username(String userName);

    List<Grade> findBySubjectNameAndSemesterAndStudent_Group_GroupId(String subject,
                                                             long semester, String groupId, Sort sort);

    Grade findOneBySubjectNameAndSemesterAndStudent_UsernameAndTeacher_Username
            (String subject, long semester, String studUsername, String teacherUsername);
}
