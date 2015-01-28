package ru.ildar.controllers.pojos;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

public class FacultyPojo
{
    @NotNull
    @Min(1)
    private Long unId;

    @NotNull
    @Size(min = 2, max = 150)
    private String facultyName;

    @NotNull
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Past
    private Date foundDate;

    public Long getUnId()
    {
        return unId;
    }

    public void setUnId(Long unId)
    {
        this.unId = unId;
    }

    public String getFacultyName()
    {
        return facultyName;
    }

    public void setFacultyName(String facultyName)
    {
        this.facultyName = facultyName;
    }

    public Date getFoundDate()
    {
        return foundDate;
    }

    public void setFoundDate(Date foundDate)
    {
        this.foundDate = foundDate;
    }
}
