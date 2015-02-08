package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "CITIES_LOCALIZED", schema = "STUDENTS_APP", catalog = "")
public class LocalizedCity
{
    private Integer id;
    private String translatedName;
    private City city;
    private Language language;

    public LocalizedCity() { }
    public LocalizedCity(Integer id, String translation)
    {
        this.id = id;
        this.translatedName = translation;
    }

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "TRANSLATED_NAME")
    public String getTranslatedName()
    {
        return translatedName;
    }

    public void setTranslatedName(String translatedName)
    {
        this.translatedName = translatedName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalizedCity that = (LocalizedCity) o;
        return Objects.equals(this.getId(), that.getId());
    }

    @Override
    public int hashCode()
    {
        return (id ^ (id >>> 32));
    }

    @ManyToOne
    @JoinColumn(name = "CITY_ID", referencedColumnName = "ID", nullable = false)
    public City getCity()
    {
        return city;
    }

    public void setCity(City city)
    {
        this.city = city;
    }

    @ManyToOne
    @JoinColumn(name = "LANGUAGE", referencedColumnName = "LANGUAGE")
    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }
}
