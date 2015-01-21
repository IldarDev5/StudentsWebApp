package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "UN_DESCRIPTION", schema = "STUDENTS_APP", catalog = "")
public class UniversityDescription
{
    private long id;
    private String description;
    private Timestamp lastChangeDate;
    private String language;
    private Person lastChangePerson;
    private University university;

    public UniversityDescription() { }
    public UniversityDescription(String description, Timestamp lastChangeDate, String language,
                                 Person lastChangePerson, University university)
    {
        this.description = description;
        this.lastChangeDate = lastChangeDate;
        this.language = language;
        this.lastChangePerson = lastChangePerson;
        this.university = university;
    }

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Basic
    @Column(name = "LAST_CHANGE_DATE")
    public Timestamp getLastChangeDate()
    {
        return lastChangeDate;
    }

    public void setLastChangeDate(Timestamp lastChangeDate)
    {
        this.lastChangeDate = lastChangeDate;
    }

    @Basic
    @Column(name = "LANGUAGE")
    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    @ManyToOne
    @JoinColumn(name = "last_change_person_username", referencedColumnName = "USERNAME", nullable = false)
    public Person getLastChangePerson()
    {
        return lastChangePerson;
    }

    public void setLastChangePerson(Person lastChangePerson)
    {
        this.lastChangePerson = lastChangePerson;
    }

    @ManyToOne
    @JoinColumn(name = "un_id", referencedColumnName = "un_id", nullable = false)
    public University getUniversity()
    {
        return university;
    }

    public void setUniversity(University university)
    {
        this.university = university;
    }

    public String lastChangeDateAsString()
    {
        return lastChangeDate != null ?
                new SimpleDateFormat("dd/MM/yyyy hh:mm").format(lastChangeDate) : "(Not set)";
    }

    public String lastChangePersonFormat()
    {
        return lastChangePerson != null ? lastChangePerson.getUsername() : "(Not set)";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UniversityDescription that = (UniversityDescription) o;
        return id == that.id;
    }

    @Override
    public int hashCode()
    {
        return (int) (id ^ (id >>> 32));
    }
}
