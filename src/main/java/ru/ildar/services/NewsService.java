package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.News;

import java.util.List;

@Service
public interface NewsService
{
    /**
     * Returns news on the specified page
     * @param page Number of page from which news are to be returned
     * @param newsCountPerPage Count of news per page
     */
    List<News> getNews(int page, int newsCountPerPage);

    /**
     * Returns single news by its ID
     */
    News getNews(int newsId);

    /**
     * Returns amount of pages of news
     * @param newsPerPage Count of news per page
     */
    Integer pagesCount(int newsPerPage);

    /**
     * Saves news instance into the database, but before this sets author to the news
     * specified by his username
     * @param news News instance to save to DB
     * @param username Username of the author to set to the news
     */
    void setAuthorAndSaveNews(News news, String username);

    /**
     * Remove single news instance from the database
     * @param newsId ID of the news to delete
     */
    void removeNews(int newsId);

    /**
     * Updates news instance in the database
     */
    void updateNews(News news);
}
