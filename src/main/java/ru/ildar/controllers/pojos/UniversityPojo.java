package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UniversityPojo
{
    @NotNull
    @Size(min = 3, max = 150)
    private String unName;

    @Size(min = 5, max = 100)
    private String unAddress;

    @NotNull
    @Min(1)
    private Integer citySelect;

    public String getUnName()
    {
        return unName;
    }

    public void setUnName(String unName)
    {
        this.unName = unName;
    }

    public String getUnAddress()
    {
        return unAddress;
    }

    public void setUnAddress(String unAddress)
    {
        this.unAddress = unAddress;
    }

    public Integer getCitySelect()
    {
        return citySelect;
    }

    public void setCitySelect(Integer citySelect)
    {
        this.citySelect = citySelect;
    }
}
