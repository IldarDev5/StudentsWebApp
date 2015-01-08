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
    private String subjectType;

    public Subject() { }
    public Subject(String subjName, SubjectType subjectType)
    {
        this.subjectName = subjName;
        this.subjectType = subjectType.getSubjectType();
    }

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

    @Column(name = "SUBJECT_TYPE")
    public String getSubjectType()
    {
        return subjectType;
    }

    public void setSubjectType(String subjectType)
    {
        this.subjectType = subjectType;
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
