package ru.ildar.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "LANGUAGES", schema = "STUDENTS_APP")
public class Language
{
    private String language;
    private String abbreviation;

    @Id
    @Column(name = "LANGUAGE", nullable = false)
    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    @Basic
    @Column(name = "LANG_ABBREV", unique = true, nullable = false)
    public String getAbbreviation() { return abbreviation; }

    public void setAbbreviation(String abbreviation) { this.abbreviation = abbreviation; }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language that = (Language) o;
        return this.language.equals(that.language);
    }

    @Override
    public int hashCode()
    {
        return language.hashCode();
    }
}
