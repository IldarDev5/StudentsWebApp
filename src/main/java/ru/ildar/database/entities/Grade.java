package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "GRADES", schema = "STUDENTS_APP", catalog = "")
public class Grade
{
    private long gradeId;
    private long gradeValue;
    private long semester;
    private Teacher teacher;
    private Student student;
    private String subject;

    public Grade() { }
    public Grade(long gradeValue, long semester, Teacher teacher, Student student, String subject)
    {
        this.gradeValue = gradeValue;
        this.semester = semester;
        this.teacher = teacher;
        this.student = student;
        this.subject = subject;
    }

    @Id
    @Column(name = "GRADE_ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
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
    public Teacher getTeacher()
    {
        return teacher;
    }

    public void setTeacher(Teacher teacher)
    {
        this.teacher = teacher;
    }

    @ManyToOne
    @JoinColumn(name = "STUD_USERNAME", referencedColumnName = "USERNAME", nullable = false)
    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }
}
