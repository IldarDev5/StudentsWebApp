package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.News;
import ru.ildar.services.NewsService;

import java.security.Principal;
import java.sql.Date;

@Controller
@RequestMapping("/admin/news")
public class NewsController
{
    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addNews()
    {
        return new ModelAndView("addNews", "newsObj", new News());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addNews(@ModelAttribute("newsObj") News news, Principal principal)
    {
        String username = principal.getName();
        news.setPublishDate(new Date(new java.util.Date().getTime()));
        newsService.setAuthorAndSaveNews(news, username);
        return new ModelAndView("redirect:/news/view?newsId=" + news.getNewsId());
    }
}
