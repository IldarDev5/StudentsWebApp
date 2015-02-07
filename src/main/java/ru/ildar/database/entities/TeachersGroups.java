package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Ildar on 07.01.15.
 */
@Entity
@Table(name = "TEACHERS_GROUPS", schema = "STUDENTS_APP", catalog = "",
        uniqueConstraints = @UniqueConstraint(columnNames =
                {"TEACHER_USERNAME", "GROUP_ID", "SUBJECT_NAME", "SEMESTER"}))
public class TeachersGroups
{
    private int id;
    private String subjectName;
    private long semester;
    private Group group;
    private Teacher teacher;

    //Transient field
    private String subjectTranslation;

    public TeachersGroups() { }
    public TeachersGroups(String subjectName, long semester, Group group, Teacher teacher)
    {
        this.subjectName = subjectName;
        this.semester = semester;
        this.group = group;
        this.teacher = teacher;
    }

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "SUBJECT_NAME", nullable = false)
    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    @Basic
    @Column(name = "SEMESTER", nullable = false)
    public long getSemester()
    {
        return semester;
    }

    public void setSemester(long semester)
    {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeachersGroups that = (TeachersGroups) o;
        return this.id == that.id;
    }

    @Override
    public int hashCode()
    {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "GROUP_ID", nullable = false)
    public Group getGroup()
    {
        return group;
    }

    public void setGroup(Group group)
    {
        this.group = group;
    }

    @ManyToOne
    @JoinColumn(name = "TEACHER_USERNAME", referencedColumnName = "USERNAME", nullable = false)
    public Teacher getTeacher()
    {
        return teacher;
    }

    public void setTeacher(Teacher teacher)
    {
        this.teacher = teacher;
    }

    @Transient
    public String getSubjectTranslation()
    {
        return subjectTranslation;
    }

    public void setSubjectTranslation(String subjectTranslation)
    {
        this.subjectTranslation = subjectTranslation;
    }
}
