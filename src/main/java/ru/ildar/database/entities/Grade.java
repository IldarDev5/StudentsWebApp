package ru.ildar.database.entities;

import javax.persistence.*;

@Entity
@Table(name = "GRADES", schema = "STUDENTS_APP", catalog = "")
public class Grade
{
    private long gradeId;
    private long gradeValue;
    private long semester;
    private Person teacher;
    private Person student;
    private String subject;

    @Id
    @Column(name = "GRADE_ID")
    public long getGradeId()
    {
        return gradeId;
    }

    public void setGradeId(long gradeId)
    {
        this.gradeId = gradeId;
    }

    @Basic
    @Column(name = "GRADE_VALUE")
    public long getGradeValue()
    {
        return gradeValue;
    }

    public void setGradeValue(long gradeValue)
    {
        this.gradeValue = gradeValue;
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

    @Basic
    @Column(name = "SUBJECT_NAME")
    public String getSubjectName()
    {
        return subject;
    }

    public void setSubjectName(String subject)
    {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return gradeId == ((Grade)o).getGradeId();
    }

    @Override
    public int hashCode()
    {
        return (int) (gradeId ^ (gradeId >>> 32));
    }

    @ManyToOne
    @JoinColumn(name = "TEACHER_USERNAME", referencedColumnName = "USERNAME", nullable = false)
    public Person getTeacher()
    {
        return teacher;
    }

    public void setTeacher(Person teacher)
    {
        this.teacher = teacher;
    }

    @ManyToOne
    @JoinColumn(name = "STUD_USERNAME", referencedColumnName = "USERNAME", nullable = false)
    public Person getStudent()
    {
        return student;
    }

    public void setStudent(Person student)
    {
        this.student = student;
    }
}
