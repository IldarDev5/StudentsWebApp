package ru.ildar.database.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Person;

public interface PersonDAO extends CrudRepository<Person, String>
{
    Page<Person> findByRoleName(String role, Pageable pageable);
    int countByRoleName(String role);
}
