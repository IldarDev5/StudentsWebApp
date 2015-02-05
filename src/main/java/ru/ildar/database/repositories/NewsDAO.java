package ru.ildar.database.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import ru.ildar.database.entities.News;

public interface NewsDAO extends PagingAndSortingRepository<News, Integer>
{

}
