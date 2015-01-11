package ru.ildar.database.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Group;
import ru.ildar.database.entities.TeachersGroups;

import java.util.List;

public interface TeachersGroupsDAO extends CrudRepository<TeachersGroups, Integer>
{
    List<TeachersGroups> findBySubjectName(String subjectName);
    List<TeachersGroups> findByGroup(Group group);
    List<TeachersGroups> findByTeacher_Username(String name, Sort sort);

    TeachersGroups findBySubjectNameAndSemesterAndGroup(String subject, long semester, Group group);

}
