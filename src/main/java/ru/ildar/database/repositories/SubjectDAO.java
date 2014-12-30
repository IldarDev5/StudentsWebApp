package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Subject;

public interface SubjectDAO extends CrudRepository<Subject, String>
{
}
