package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.News;
import ru.ildar.services.NewsService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Controller that provides ability to view news for every role(and unauthenticated users, too)
 */
@Controller
@RequestMapping("/news")
public class ViewNewsController
{
    public static final int newsPerPage = 5;

    private NewsService newsService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        newsService = serviceFactory.getNewsService();
    }

    @RequestMapping(value = "{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public List<News> news(@PathVariable("pageNumber") Integer page)
    {
        //Path variable pageNumber is 1-based, input parameter in PageRequest is 0-based
        List<News> news = newsService.getNews(page - 1, newsPerPage);
        //Setting full description to null because it won't be needed while viewing news briefly
        news.stream().forEach((n) -> n.setFullDescription(null));
        return news;
    }

    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ModelAndView viewNews(@RequestParam("newsId") int newsId)
    {
        News news = newsService.getNews(newsId);
        if(news == null)
        //news == null -> Either user has entered ID of news that doesn't exist in the DB,
        //or user was already watching edit page of this news and he removed this news from
        //the panel on the right
        {
            return new ModelAndView("redirect:/startPage");
        }
        return new ModelAndView("common/news/viewNews", "newsObj", news);
    }
}
