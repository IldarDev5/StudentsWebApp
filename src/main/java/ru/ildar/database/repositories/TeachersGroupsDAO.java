package ru.ildar.database.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Group;
import ru.ildar.database.entities.TeachersGroups;

import java.util.List;

/**
 * Repository for performing CRUD operations on the TEACHERS_GROUPS table
 */
public interface TeachersGroupsDAO extends CrudRepository<TeachersGroups, Integer>
{
    List<TeachersGroups> findBySubjectName(String subjectName);
    List<TeachersGroups> findByGroup(Group group);
    List<TeachersGroups> findByTeacher_Username(String name, Sort sort);

    TeachersGroups findBySubjectNameAndSemesterAndGroupAndTeacher_Username
            (String subject, long semester, Group group, String teacherUsername);
}
