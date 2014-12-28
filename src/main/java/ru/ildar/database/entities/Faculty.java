package ru.ildar.database.entities;

import javax.persistence.*;

/**
 * Created by Ildar on 28.12.14.
 */
@Entity
@Table(name = "FACULTIES", schema = "STUDENTS_APP", catalog = "")
public class Faculty
{
    private long facultyId;
    private String facultyName;
    private University university;

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
