package ru.ildar.controllers.teacher;

public class StudentGradePojo
{
    private String groupId;
    private String subject;
    private int semester;
    private String studentSelect;
    private int gradeValue;

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public int getSemester()
    {
        return semester;
    }

    public void setSemester(int semester)
    {
        this.semester = semester;
    }

    public String getStudentSelect()
    {
        return studentSelect;
    }

    public void setStudentSelect(String studentSelect)
    {
        this.studentSelect = studentSelect;
    }

    public int getGradeValue()
    {
        return gradeValue;
    }

    public void setGradeValue(int gradeValue)
    {
        this.gradeValue = gradeValue;
    }
}
