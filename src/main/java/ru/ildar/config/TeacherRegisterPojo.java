package ru.ildar.config;

public class TeacherRegisterPojo
{
    private String username;
    private String password;
    private String repeatPassword;
    private String role;
    private int uniId;
    private int city;

    public TeacherRegisterPojo() { }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRepeatPassword()
    {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword)
    {
        this.repeatPassword = repeatPassword;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public int getUniId()
    {
        return uniId;
    }

    public void setUniId(int uniId)
    {
        this.uniId = uniId;
    }

    public int getCity()
    {
        return city;
    }

    public void setCity(int city)
    {
        this.city = city;
    }
}
