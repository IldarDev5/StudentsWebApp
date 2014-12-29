package ru.ildar.database.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

@Entity
@Table(name = "PEOPLE_DETAILS", schema = "STUDENTS_APP", catalog = "")
public class PersonDetails
{
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String title;
    private Timestamp enrollment;
    private Faculty faculty;
    private byte[] personPhoto;
    private Person person;

    public PersonDetails() { }
    public PersonDetails(String username, String firstName, String lastName,
                         String email, String title, Timestamp enrollment, byte[] personPhoto)
    {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        this.enrollment = enrollment;
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
    @Column(name = "ENROLLMENT")
    public Timestamp getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(Timestamp enrollment)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonDetails that = (PersonDetails) o;

        return username.equals(that.getUsername());
    }

    @Override
    public int hashCode()
    {
        return username.hashCode();
    }

    @OneToOne
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME", nullable = false)
    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    @ManyToOne
    @JoinColumn(name = "FACULTY_ID", referencedColumnName = "FACULTY_ID", nullable = false)
    public Faculty getFaculty() { return faculty; }

    public void setFaculty(Faculty faculty) { this.faculty = faculty; }
}
