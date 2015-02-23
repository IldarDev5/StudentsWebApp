package ru.ildar.database.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Group;
import ru.ildar.database.entities.TeachersGroups;

import java.util.List;

/**
 * Repository for performing CRUD operations on the TEACHERS_GROUPS table
 */
public interface TeachersGroupsDAO extends CrudRepository<TeachersGroups, Integer>,
        QueryDslPredicateExecutor<TeachersGroups>
{
    List<TeachersGroups> findBySubjectName(String subjectName);
    List<TeachersGroups> findByGroup(Group group);
}
