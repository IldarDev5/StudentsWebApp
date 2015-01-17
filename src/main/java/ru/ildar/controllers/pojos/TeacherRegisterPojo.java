package ru.ildar.controllers.pojos;

public class TeacherRegisterPojo
{
    private String username;
    private String password;
    private String repeatPassword;
    private String role;
    private int uniSelect;
    private int citySelect;

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

    public int getUniSelect()
    {
        return uniSelect;
    }

    public void setUniSelect(int uniSelect)
    {
        this.uniSelect = uniSelect;
    }

    public int getCitySelect()
    {
        return citySelect;
    }

    public void setCitySelect(int citySelect)
    {
        this.citySelect = citySelect;
    }
}
