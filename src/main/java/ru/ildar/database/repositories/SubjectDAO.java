package ru.ildar.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.Subject;

public interface SubjectDAO extends PagingAndSortingRepository<Subject, String>
{
}
