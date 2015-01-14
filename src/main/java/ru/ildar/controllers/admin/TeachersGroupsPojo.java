package ru.ildar.controllers.admin;

public class TeachersGroupsPojo
{
    private String subjectName;
    private int semester;
    private String citySelect;
    private String uniSelect;
    private String facSelect;
    private String groupSelect;
    private String teacher;

    public String getTeacher()
    {
        return teacher;
    }

    public void setTeacher(String teacher)
    {
        this.teacher = teacher;
    }

    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    public int getSemester()
    {
        return semester;
    }

    public void setSemester(int semester)
    {
        this.semester = semester;
    }

    public String getCitySelect()
    {
        return citySelect;
    }

    public void setCitySelect(String citySelect)
    {
        this.citySelect = citySelect;
    }

    public String getUniSelect()
    {
        return uniSelect;
    }

    public void setUniSelect(String uniSelect)
    {
        this.uniSelect = uniSelect;
    }

    public String getFacSelect()
    {
        return facSelect;
    }

    public void setFacSelect(String facSelect)
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
