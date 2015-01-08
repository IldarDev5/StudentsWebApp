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
@Table(name = "STUDENTS", schema = "STUDENTS_APP", catalog = "")
public class Student
{
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Date enrollment;
    private byte[] personPhoto;
    private Group group;

    public Student(){  }
    public Student(String username, String firstName, String lastName, String email,
                   Date enrollment, byte[] personPhoto, Group group)
    {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.enrollment = enrollment;
        this.personPhoto = personPhoto;
        this.group = group;
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
    @Column(name = "ENROLLMENT")
    public Date getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Date enrollment)
    {
        this.enrollment = enrollment;
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

    public String enrollmentDateAsString()
    {
        return new SimpleDateFormat("dd/MM/yyyy").format(enrollment);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;
        return this.username.equals(student.getUsername());
    }

    @Override
    public int hashCode()
    {
        return username.hashCode();
    }

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", nullable = false)
    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group groupsByGroupId)
    {
        this.group = groupsByGroupId;
    }
}
