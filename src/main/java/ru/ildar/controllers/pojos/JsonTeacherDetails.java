package ru.ildar.controllers.pojos;

public class JsonTeacherDetails
{
    private String firstName;
    private String lastName;
    private String email;
    private String workStart;
    private String title;
    private String unId;

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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getWorkStart()
    {
        return workStart;
    }

    public void setWorkStart(String workStart)
    {
        this.workStart = workStart;
    }

    public String getUnId()
    {
        return unId;
    }

    public void setUnId(String unId)
    {
        this.unId = unId;
    }
}