package ru.ildar.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Ildar on 29.12.14.
 */
@Entity
@Table(name = "SUBJECTS", schema = "STUDENTS_APP", catalog = "")
public class Subject
{
    private String subjectName;

    public Subject() { }
    public Subject(String subjName) { this.subjectName = subjName; }

    @Id
    @Column(name = "SUBJECT_NAME")
    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subject subject = (Subject) o;

        return subjectName.equals(subject.subjectName);
    }

    @Override
    public int hashCode()
    {
        return subjectName.hashCode();
    }
}
