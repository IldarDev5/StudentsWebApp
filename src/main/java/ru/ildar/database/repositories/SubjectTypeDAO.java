package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.SubjectType;

public interface SubjectTypeDAO extends CrudRepository<SubjectType, String>
{
}
