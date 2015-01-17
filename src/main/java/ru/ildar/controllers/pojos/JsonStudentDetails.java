package ru.ildar.controllers.pojos;

public class JsonStudentDetails
{
    private String firstName;
    private String lastName;
    private String email;
    private String enrollment;

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEnrollment()
    {
        return enrollment;
    }

    public void setEnrollment(String enrollment)
    {
        this.enrollment = enrollment;
    }
}