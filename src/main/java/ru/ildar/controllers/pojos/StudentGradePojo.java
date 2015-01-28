package ru.ildar.controllers.pojos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentGradePojo
{
    @NotNull
    @Size(min = 2, max = 20)
    private String groupId;

    @NotNull
    @Size(min = 2, max = 100)
    private String subject;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer semester;

    @NotNull
    @Size(min = 2, max = 70)
    private String studentSelect;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer gradeValue;

    public StudentGradePojo() { gradeValue = 0; }
    public StudentGradePojo(String groupId, String subject, int semester, String student, Integer gradeVal)
    {
        this.groupId = groupId;
        this.subject = subject;
        this.semester = semester;
        this.studentSelect = student;
        this.gradeValue = gradeVal;

        if(gradeVal == null)
            gradeValue = 0;
    }

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

    public Integer getSemester()
    {
        return semester;
    }

    public void setSemester(Integer semester)
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

    public Integer getGradeValue()
    {
        return gradeValue;
    }

    public void setGradeValue(Integer gradeValue)
    {
        this.gradeValue = gradeValue;
    }
}
