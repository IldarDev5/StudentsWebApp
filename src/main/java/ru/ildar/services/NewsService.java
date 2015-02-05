package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.News;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.NewsDAO;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService
{
    @Autowired
    private NewsDAO newsDAO;
    @Autowired
    private PersonService personService;

    public List<News> getNews(int page, int newsCountPerPage)
    {
        Page<News> news = newsDAO.findAll(new PageRequest(page, newsCountPerPage,
                new Sort(Sort.Direction.DESC, "publishDate")));
        List<News> result = new ArrayList<>();
        news.forEach(result::add);
        return result;
    }

    public News getNews(int newsId)
    {
        return newsDAO.findOne(newsId);
    }

    public Integer pagesCount(int newsPerPage)
    {
        return (int)Math.ceil((double)newsDAO.count() / newsPerPage);
    }

    public void setAuthorAndSaveNews(News news, String username)
    {
        Person person = personService.getByUserName(username);
        news.setAuthor(person);
        newsDAO.save(news);
    }
}
