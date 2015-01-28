package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UniversityInfoPojo
{
    @NotNull
    @Min(1)
    private Long cityId;

    @NotNull
    @Min(1)
    private Long unId;

    public Long getCityId()
    {
        return cityId;
    }

    public void setCityId(Long cityId)
    {
        this.cityId = cityId;
    }

    public Long getUnId()
    {
        return unId;
    }

    public void setUnId(Long unId)
    {
        this.unId = unId;
    }
}