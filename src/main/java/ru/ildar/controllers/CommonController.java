package ru.ildar.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class CommonController
{
    @ModelAttribute("images")
    public List<Image> images()
    {
        return Arrays.asList(
                new Image("/images/unis/kfu.jpg", "Kazan Federal University"),
                new Image("/images/unis/kit.jpg", "Karlsruhe Institute of Technology"),
                new Image("/images/unis/harvard.jpg", "Harvard University"));
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
