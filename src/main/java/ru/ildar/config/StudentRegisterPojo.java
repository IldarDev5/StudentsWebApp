package ru.ildar.config;

public class StudentRegisterPojo
{
    private String username;
    private String password;
    private String repeatPassword;
    private String role;
    private String groupId;

    public StudentRegisterPojo() { }
    public StudentRegisterPojo(String username, String password, String repeatPassword, String role, String groupId)
    {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.role = role;
        this.groupId = groupId;
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

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }
}
