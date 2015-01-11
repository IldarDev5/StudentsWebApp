package ru.ildar.controllers.admin;

public class UniversityPojo
{
    private String unName;
    private String unAddress;
    private int citySelect;

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

    public int getCitySelect()
    {
        return citySelect;
    }

    public void setCitySelect(int citySelect)
    {
        this.citySelect = citySelect;
    }
}
