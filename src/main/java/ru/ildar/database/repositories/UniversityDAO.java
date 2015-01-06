package ru.ildar.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.University;

import java.util.List;

public interface UniversityDAO extends PagingAndSortingRepository<University, Long>
{
    Iterable<University> findByCity_Id(int cityId);
}
