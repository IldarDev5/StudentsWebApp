package ru.ildar.database.repositories;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.University;

import java.util.List;

public interface UniversityDAO extends CrudRepository<University, Long>
{
    University findByUnName(String unName);
    List<University> find(PageRequest pageRequest);
}
