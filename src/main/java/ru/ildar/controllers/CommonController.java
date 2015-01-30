package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@ControllerAdvice
public class CommonController
{
    @Autowired
    private MessageSource messageSource;

    @ModelAttribute("images")
    public List<Image> images()
    {
        Locale locale = LocaleContextHolder.getLocale();
        String welcome = messageSource.getMessage("imgMsg.welcome", new Object[0], locale);
        return Arrays.asList(
                new Image("/images/home_1.jpg", welcome),
                new Image("/images/home_2.jpg", welcome));
    }

    @ModelAttribute("utils")
    public RequestUtils utils()
    {
        return new RequestUtils();
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

    public static class Image
    {
        private String src;
        private String text;

        public Image(String src, String text)
        {
            this.src = src;
            this.text = text;
        }

        public String getSrc()
        {
            return src;
        }

        public void setSrc(String src)
        {
            this.src = src;
        }

        public String getText()
        {
            return text;
        }

        public void setText(String text)
        {
            this.text = text;
        }
    }
}
