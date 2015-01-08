package ru.ildar.config;

public class TeacherRegisterPojo
{
    private String username;
    private String password;
    private String repeatPassword;
    private String role;

    public TeacherRegisterPojo() { }
    public TeacherRegisterPojo(String username, String password, String repeatPassword, String role)
    {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.role = role;
    }

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
}
