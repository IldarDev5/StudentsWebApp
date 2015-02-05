package ru.ildar.controllers.pojos;

public class Image
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