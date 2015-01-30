package ru.ildar.database.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.University;

/**
 * Repository for performing CRUD operations on the FACULTIES table
 */
public interface FacultyDAO extends CrudRepository<Faculty, Long>
{
    Iterable<Faculty> findByUniversity_UnId(long universityId);

    @Query("select sum(f.studentsCount) from Faculty f where f.university.unId = :unId")
    int sumByUniversity_UnId(@Param("unId") Long unId);

    Faculty findByUniversity_UnIdAndFacultyName(long unId, String facultyName);
}
