package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UniversityInfoPojo
{
    @NotNull
    @Min(1)
    private Integer cityId;

    @NotNull
    @Min(1)
    private Integer unId;

    public Integer getCityId()
    {
        return cityId;
    }

    public void setCityId(Integer cityId)
    {
        this.cityId = cityId;
    }

    public Integer getUnId()
    {
        return unId;
    }

    public void setUnId(Integer unId)
    {
        this.unId = unId;
    }
}