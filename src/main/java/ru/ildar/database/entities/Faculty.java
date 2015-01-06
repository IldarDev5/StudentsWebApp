package ru.ildar.database.entities;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "FACULTIES", schema = "STUDENTS_APP", catalog = "")
public class Faculty
{
    private long facultyId;
    private String facultyName;
    private Date foundDate;
    private int studentsCount;
    private int teachersCount;
    private University university;

    public Faculty() { }
    public Faculty(String facultyName, Date foundDate, University university)
    {
        this.facultyName = facultyName;
        this.foundDate = foundDate;
        this.university = university;
    }

    @Id
    @Column(name = "FACULTY_ID")
    public long getFacultyId()
    {
        return facultyId;
    }

    public void setFacultyId(long facultyId)
    {
        this.facultyId = facultyId;
    }

    @Basic
    @Column(name = "FACULTY_NAME")
    public String getFacultyName()
    {
        return facultyName;
    }

    public void setFacultyName(String facultyName)
    {
        this.facultyName = facultyName;
    }

    @Basic
    @Column(name = "STUDENTS_COUNT")
    public int getStudentsCount()
    {
        return studentsCount;
    }

    public void setStudentsCount(int studentsCount)
    {
        this.studentsCount = studentsCount;
    }

    @Basic
    @Column(name = "TEACHERS_COUNT")
    public int getTeachersCount() { return teachersCount; }

    public void setTeachersCount(int teachersCount) { this.teachersCount = teachersCount; }

    @Basic
    @Column(name = "FOUND_DATE")
    public Date getFoundDate()
    {
        return foundDate;
    }

    public void setFoundDate(Date foundDate)
    {
        this.foundDate = foundDate;
    }

    public String foundDateAsString()
    {
        if(foundDate == null)
            return "";

        return new SimpleDateFormat("dd/MM/yyyy").format(foundDate);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Faculty faculty = (Faculty) o;

        return facultyId == faculty.getFacultyId();
    }

    @Override
    public int hashCode()
    {
        return (int) (facultyId ^ (facultyId >>> 32));
    }

    @ManyToOne
    @JoinColumn(name = "UN_ID", referencedColumnName = "UN_ID", nullable = false)
    public University getUniversity()
    {
        return university;
    }

    public void setUniversity(University university)
    {
        this.university = university;
    }
}
