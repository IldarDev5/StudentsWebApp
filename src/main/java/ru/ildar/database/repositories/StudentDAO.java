package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.Student;

import java.util.List;

public interface StudentDAO extends CrudRepository<Student, String>
{
    List<Student> getByGroup_GroupId(String groupId);
}
