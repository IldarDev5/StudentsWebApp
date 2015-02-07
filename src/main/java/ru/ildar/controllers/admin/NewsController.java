package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.News;
import ru.ildar.services.NewsService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.sql.Timestamp;

@Controller
@RequestMapping("/admin/news")
public class NewsController
{
    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addNews()
    {
        return new ModelAndView("addNews", "newsObj", new News());
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addNews(@ModelAttribute("newsObj") @Valid News news,
                                BindingResult result, Principal principal)
            throws UnsupportedEncodingException
    {
        if(result.hasErrors())
        {
            return new ModelAndView("addNews", "newsObj", news);
        }

        String username = principal.getName();
        news.setPublishDate(new Timestamp(new java.util.Date().getTime()));
        news.setBriefDescription(new String(news.getBriefDescription().getBytes("ISO-8859-1"), "UTF-8"));
        news.setFullDescription(new String(news.getFullDescription().getBytes("ISO-8859-1"), "UTF-8"));
        newsService.setAuthorAndSaveNews(news, username);
        return new ModelAndView("redirect:/news/view?newsId=" + news.getNewsId());
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String remove(@RequestParam("layoutNewsId") int newsId, HttpServletRequest request)
    {
        newsService.removeNews(newsId);
        String referer = request.getHeader("referer");
        return "redirect:" + (referer != null ? referer : "/startPage");
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editNews(@RequestParam("newsId") int newsId, Principal principal)
    {
        News news = newsService.getNews(newsId);
        if(news == null)
            //news == null -> Either user has entered ID of news that doesn't exist in the DB,
            //or user was already watching edit page of this news and he removed this news from
            //the panel on the right
        {
            return new ModelAndView("redirect:/startPage");
        }

        if(!news.getAuthor().getUsername().equals(principal.getName()))
            //Checking that user that tries to edit the news is the same user that created it
        {
            throw new ResourceAccessDeniedException();
        }

        return new ModelAndView("editNews", "newsObj", news);
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @PreAuthorize("#news.author.username == authentication.name")
    public ModelAndView editNews(@ModelAttribute("newsObj") @Valid News news, BindingResult result)
            throws UnsupportedEncodingException
    {
        if(result.hasErrors())
        {
            return new ModelAndView("editNews", "newsObj", news);
        }

        news.setBriefDescription(new String(news.getBriefDescription().getBytes("ISO-8859-1"), "UTF-8"));
        news.setFullDescription(new String(news.getFullDescription().getBytes("ISO-8859-1"), "UTF-8"));
        newsService.updateNews(news);
        return new ModelAndView("redirect:/startPage");
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    private static class ResourceAccessDeniedException extends RuntimeException
    {

    }
}
