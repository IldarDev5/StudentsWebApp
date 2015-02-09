package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UniversityInfoPojo
{
    @NotNull
    @Min(1)
    private Integer cityId;

    @NotNull
    @Min(1)
    private Integer unId;

    @Size(min = 0, max = 100)
    private String address;

    public UniversityInfoPojo() { }
    public UniversityInfoPojo(Integer cityId, Integer unId, String address)
    {
        this.cityId = cityId;
        this.unId = unId;
        this.address = address;
    }

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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}