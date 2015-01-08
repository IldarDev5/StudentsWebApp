package ru.ildar.database.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Created by Ildar on 07.01.15.
 */
@Entity
@Table(name = "TEACHERS", schema = "STUDENTS_APP", catalog = "")
public class Teacher
{
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private University university;
    private Date workStart;
    private byte[] personPhoto;

    public Teacher() { }
    public Teacher(String username, String firstName, String lastName, String email,
                   String title, byte[] personPhoto, Date workStart, University university)
    {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.university = university;
        this.workStart = workStart;
        this.personPhoto = personPhoto;
    }

    @Id
    @Column(name = "USERNAME")
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME")
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @Basic
    @Column(name = "TITLE")
    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Basic
    @Column(name = "WORK_START")
    public Date getWorkStart()
    {
        return workStart;
    }

    public void setWorkStart(Date workStart)
    {
        this.workStart = workStart;
    }

    @Basic
    @Column(name = "PERSON_PHOTO")
    public byte[] getPersonPhoto()
    {
        return personPhoto;
    }

    public void setPersonPhoto(byte[] personPhoto)
    {
        this.personPhoto = personPhoto;
    }

    public String workStartDateAsString()
    {
        return new SimpleDateFormat("dd/MM/yyyy").format(workStart);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher teacher = (Teacher) o;
        return this.username.equals(teacher.getUsername());
    }

    @Override
    public int hashCode()
    {
        return username.hashCode();
    }

    @ManyToOne
    @JoinColumn(name = "UNIVERSITY_ID", referencedColumnName = "UN_ID", nullable = false)
    public University getUniversity()
    {
        return university;
    }

    public void setUniversity(University university)
    {
        this.university = university;
    }
}
