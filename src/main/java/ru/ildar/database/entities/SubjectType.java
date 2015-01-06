package ru.ildar.database.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SUBJECT_TYPES")
public class SubjectType
{
    private String subjectType;

    @Id
    @Column(name = "SUBJECT_TYPE")
    public String getSubjectType()
    {
        return subjectType;
    }

    public void setSubjectType(String subjectType)
    {
        this.subjectType = subjectType;
    }
}
