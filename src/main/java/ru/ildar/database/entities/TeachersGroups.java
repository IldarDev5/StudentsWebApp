package ru.ildar.database.entities;

import javax.persistence.*;

/**
 * Created by Ildar on 07.01.15.
 */
@Entity
@Table(name = "TEACHERS_GROUPS", schema = "STUDENTS_APP", catalog = "")
public class TeachersGroups
{
    private int id;
    private String subjectName;
    private long semester;
    private Group group;
    private Teacher teacher;

    @Id
    @Column(name = "ID")
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Basic
    @Column(name = "SUBJECT_NAME")
    public String getSubjectName()
    {
        return subjectName;
    }

    public void setSubjectName(String subjectName)
    {
        this.subjectName = subjectName;
    }

    @Basic
    @Column(name = "SEMESTER")
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
}
