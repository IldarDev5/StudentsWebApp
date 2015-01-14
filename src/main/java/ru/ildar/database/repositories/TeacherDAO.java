package ru.ildar.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.Teacher;

import java.util.List;

public interface TeacherDAO extends PagingAndSortingRepository<Teacher, String>
{
    List<Teacher> findByUniversity_UnId(long uniId);
}
