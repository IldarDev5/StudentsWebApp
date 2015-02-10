package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LocalizedSubjectPojo
{
    @Min(1)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 100)
    private String subjectName;

    @NotNull
    @Size(min = 2, max = 25)
    private String languageAbbrev;

    @NotNull
    @Size(min = 0, max = 100)
    private String subjectTranslation;

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public String getLanguageAbbrev()
    {
        return languageAbbrev;
    }

    public void setLanguageAbbrev(String languageAbbrev)
    {
        this.languageAbbrev = languageAbbrev;
    }

    public String getSubjectTranslation()
    {
        return subjectTranslation;
    }

    public void setSubjectTranslation(String subjectTranslation)
    {
        this.subjectTranslation = subjectTranslation;
    }
}
