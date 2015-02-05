package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "NEWS", schema = "STUDENTS_APP")
public class News
{
    private int newsId;
    private Person author;
    private Timestamp publishDate;

    @NotNull
    @Size(min = 2, max = 150)
    private String briefDescription;

    @NotNull
    @Size(min = 2, max = 1500)
    private String fullDescription;

    public News() { }

    @Id
    @Column(name = "NEWS_ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public int getNewsId()
    {
        return newsId;
    }

    public void setNewsId(int newsId)
    {
        this.newsId = newsId;
    }

    @ManyToOne
    @JoinColumn(name = "AUTHOR_USERNAME", referencedColumnName = "USERNAME", nullable = false)
    public Person getAuthor()
    {
        return author;
    }

    public void setAuthor(Person author)
    {
        this.author = author;
    }

    @Column(name = "PUBLISH_DATE")
    public Timestamp getPublishDate()
    {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate)
    {
        this.publishDate = publishDate;
    }

    @Transient
    public String getPublishDateAsString()
    {
        if(publishDate == null)
            return "";

        return new SimpleDateFormat("dd/MM/yyyy").format(publishDate);
    }

    @Column(name = "BRIEF_DESCRIPTION")
    public String getBriefDescription()
    {
        return briefDescription;
    }

    public void setBriefDescription(String briefDescription)
    {
        this.briefDescription = briefDescription;
    }

    @Column(name = "FULL_DESCRIPTION")
    public String getFullDescription()
    {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription)
    {
        this.fullDescription = fullDescription;
    }
}
