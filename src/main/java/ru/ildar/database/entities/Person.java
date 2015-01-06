package ru.ildar.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "PEOPLE", schema = "STUDENTS_APP", catalog = "")
public class Person implements Comparable<Person>
{
    private String username;
    private String password;
    private String role;
    private PersonDetails details;

    public Person() { }
    public Person(String username, String password, String role)
    {
        this.username = username;
        this.password = password;
        this.role = role;
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
    @Column(name = "PASSWORD")
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Basic
    @Column(name = "ROLE_NAME")
    public String getRoleName()
    {
        return role;
    }

    public void setRoleName(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return username.equals(person.getUsername());
    }

    @Override
    public int hashCode()
    {
        return username.hashCode();
    }

    @OneToOne(mappedBy = "person", cascade = CascadeType.REMOVE)
    public PersonDetails getDetails()
    {
        return details;
    }

    public void setDetails(PersonDetails details)
    {
        this.details = details;
    }

    @Override
    public int compareTo(Person p2)
    {
        return this.getUsername().compareTo(p2.getUsername());
    }
}
