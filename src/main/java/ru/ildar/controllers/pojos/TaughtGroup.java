package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TaughtGroup
{
    @NotNull
    @Min(1)
    private Integer citySelect;

    @NotNull
    @Min(1)
    private Integer uniSelect;

    @NotNull
    @Size(min = 2, max = 70)
    private String teacherSelect;

    public Integer getCitySelect()
    {
        return citySelect;
    }

    public void setCitySelect(Integer citySelect)
    {
        this.citySelect = citySelect;
    }

    public Integer getUniSelect()
    {
        return uniSelect;
    }

    public void setUniSelect(Integer uniSelect)
    {
        this.uniSelect = uniSelect;
    }

    public String getTeacherSelect()
    {
        return teacherSelect;
    }

    public void setTeacherSelect(String teacherSelect)
    {
        this.teacherSelect = teacherSelect;
    }
}
