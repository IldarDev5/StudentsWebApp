package ru.ildar.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ildar.database.entities.Faculty;

/**
 * Repository for performing CRUD operations on the FACULTIES table
 */
public interface FacultyDAO extends CrudRepository<Faculty, Integer>, QueryDslPredicateExecutor<Faculty>
{
    @Query("select sum(f.studentsCount) from Faculty f where f.university.unId = :unId")
    int sumByUniversity_UnId(@Param("unId") int unId);
}
