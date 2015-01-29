package ru.ildar.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.University;

import java.util.List;

/**
 * Repository for performing CRUD operations on the UNIVERSITIES table
 */
public interface UniversityDAO extends PagingAndSortingRepository<University, Long>
{
    Iterable<University> findByCity_Id(int cityId);
    University findByCity_IdAndUnName(int cityId, String unName);
}
