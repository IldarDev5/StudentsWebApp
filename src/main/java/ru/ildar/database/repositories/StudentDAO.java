package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Student;

public interface StudentDAO extends CrudRepository<Student, String>
{
}
