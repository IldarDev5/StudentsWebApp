package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LocalizedCityPojo
{
    @NotNull
    @Min(1)
    private Integer cityId;

    @Min(1)
    private Integer locCityId;

    @NotNull
    @Size(min = 2, max = 25)
    private String language;

    @NotNull
    @Size(min = 0, max = 50)
    private String cityTranslation;

    public Integer getCityId()
    {
        return cityId;
    }

    public void setCityId(Integer cityId)
    {
        this.cityId = cityId;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getCityTranslation()
    {
        return cityTranslation;
    }

    public void setCityTranslation(String cityTranslation)
    {
        this.cityTranslation = cityTranslation;
    }

    public Integer getLocCityId()
    {
        return locCityId;
    }

    public void setLocCityId(Integer locCityId)
    {
        this.locCityId = locCityId;
    }
}
