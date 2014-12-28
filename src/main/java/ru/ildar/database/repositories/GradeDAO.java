package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Grade;

public interface GradeDAO extends CrudRepository<Grade, Long>
{

}
