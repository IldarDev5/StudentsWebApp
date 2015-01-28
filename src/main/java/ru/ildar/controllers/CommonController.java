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
