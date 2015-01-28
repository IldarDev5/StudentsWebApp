package ru.ildar.controllers.pojos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TeachersGroupsPojo
{
    @NotNull
    @Size(min = 2, max = 100)
    private String subjectName;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer semester;

    @NotNull
    @Min(1)
    private Integer citySelect;

    @NotNull
    @Min(1)
    private Integer uniSelect;

    @NotNull
    @Min(1)
    private Integer facSelect;

    @NotNull
    @Size(min = 2, max = 20)
    private String groupSelect;

    @NotNull
    @Size(min = 2, max = 70)
    private String teacherSelect;

    public String getTeacherSelect()
    {
        return teacherSelect;
    }

    public void setTeacherSelect(String teacherSelect)
    {
        this.teacherSelect = teacherSelect;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public Integer getSemester()
    {
        return semester;
    }

    public void setSemester(Integer semester)
    {
        this.semester = semester;
    }

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

    public Integer getFacSelect()
    {
        return facSelect;
    }

    public void setFacSelect(Integer facSelect)
    {
        this.facSelect = facSelect;
    }

    public String getGroupSelect()
    {
        return groupSelect;
    }

    public void setGroupSelect(String groupSelect)
    {
        this.groupSelect = groupSelect;
    }
}
