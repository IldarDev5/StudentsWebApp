package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.News;
import ru.ildar.database.entities.Person;
import ru.ildar.database.repositories.NewsDAO;
import ru.ildar.services.NewsService;
import ru.ildar.services.factory.impl.JpaServiceFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsServiceJpaImpl implements NewsService
{
    @Autowired
    private NewsDAO newsDAO;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public List<News> getNews(int page, int newsCountPerPage)
    {
        Page<News> news = newsDAO.findAll(new PageRequest(page, newsCountPerPage,
                new Sort(Sort.Direction.DESC, "publishDate")));
        List<News> result = new ArrayList<>();
        news.forEach(result::add);
        return result;
    }

    @Override
    public News getNews(int newsId)
    {
        return newsDAO.findOne(newsId);
    }

    @Override
    public Integer pagesCount(int newsPerPage)
    {
        return (int)Math.ceil((double)newsDAO.count() / newsPerPage);
    }

    @Override
    public void setAuthorAndSaveNews(News news, String username)
    {
        Person person = serviceFactory.getPersonService().getByUserName(username);
        news.setAuthor(person);
        newsDAO.save(news);
    }

    @Override
    public void removeNews(int newsId)
    {
        newsDAO.delete(newsId);
    }

    @Override
    public void updateNews(News news)
    {
        News prevNews = newsDAO.findOne(news.getNewsId());
        if(prevNews != null)
        {
            prevNews.setBriefDescription(news.getBriefDescription());
            prevNews.setFullDescription(news.getFullDescription());
        }
    }
}
