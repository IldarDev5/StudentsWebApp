package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.Teacher;

public interface TeacherDAO extends PagingAndSortingRepository<Teacher, String>
{
}
