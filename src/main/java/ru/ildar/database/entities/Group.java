package ru.ildar.database.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "GROUPS", schema = "STUDENTS_APP", catalog = "")
public class Group
{
    private String groupId;
    private long studentsCount;
    private Collection<Student> students;
    private Faculty faculty;

    public Group() { }
    public Group(String groupId) { this.groupId = groupId; }

    @Id
    @Column(name = "GROUP_ID")
    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
        this.studentsCount = 0;
    }

    @Basic
    @Column(name = "STUDENTS_COUNT")
    public Long getStudentsCount()
    {
        return studentsCount;
    }

    public void setStudentsCount(Long studentsCount)
    {
        this.studentsCount = studentsCount;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;
        return this.groupId.equals(group.getGroupId());
    }

    @Override
    public int hashCode()
    {
        return this.groupId.hashCode();
    }

    @OneToMany(mappedBy = "group")
    public Collection<Student> getStudents()
    {
        return students;
    }

    public void setStudents(Collection<Student> students)
    {
        this.students = students;
    }

    @ManyToOne
    @JoinColumn(name = "FACULTY_ID", referencedColumnName = "FACULTY_ID", nullable = false)
    public Faculty getFaculty()
    {
        return faculty;
    }

    public void setFaculty(Faculty faculty)
    {
        this.faculty = faculty;
    }
}
