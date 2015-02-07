package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.ildar.database.entities.News;
import ru.ildar.services.NewsService;

import java.util.List;

@ControllerAdvice
public class CommonController
{
    @Autowired
    private NewsService newsService;

    @ModelAttribute("utils")
    public RequestUtils utils()
    {
        return new RequestUtils();
    }

    @ModelAttribute("news")
    public List<News> news()
    {
        return newsService.getNews(0, ViewNewsController.newsPerPage);
    }

    @ModelAttribute("newsPagesCount")
    public Integer pagesCount()
    {
        return newsService.pagesCount(ViewNewsController.newsPerPage);
    }

    public static class RequestUtils
    {
        public String removeLang(String query)
        {
            StringBuilder result = new StringBuilder();
            String[] params = query.split("&");
            for (String s : params)
            {
                if(s.startsWith("lang="))
                    continue;
                result.append("&" + s);
            }

            return result.length() == 0 ? result.toString() : result.substring(1);
        }
    }
}
