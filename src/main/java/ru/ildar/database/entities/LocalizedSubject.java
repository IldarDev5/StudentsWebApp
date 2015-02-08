package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "SUBJECTS_LOCALIZED", schema = "STUDENTS_APP",
        uniqueConstraints = @UniqueConstraint(columnNames = {"SUBJECT_NAME", "LANGUAGE"}))
public class LocalizedSubject
{
    private Integer id;
    private Subject subject;
    private String subjectTranslation;
    private Language language;

    public LocalizedSubject() { }
    public LocalizedSubject(Integer id, Subject subject, String subjectTranslation, Language language)
    {
        this.id = id;
        this.subject = subject;
        this.subjectTranslation = subjectTranslation;
        this.language = language;
    }

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }


    @ManyToOne
    @JoinColumn(name = "subject_name", referencedColumnName = "subject_name", nullable = false)
    public Subject getSubject()
    {
        return subject;
    }

    public void setSubject(Subject subject)
    {
        this.subject = subject;
    }

    @Column(name = "subject_translation", nullable = false)
    public String getSubjectTranslation()
    {
        return subjectTranslation;
    }

    public void setSubjectTranslation(String subjectTranslation)
    {
        this.subjectTranslation = subjectTranslation;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalizedSubject that = (LocalizedSubject) o;
        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode()
    {
        return id != null ? id.hashCode() : 0;
    }

    @ManyToOne
    @JoinColumn(name = "language", referencedColumnName = "language", nullable = false)
    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }
}
