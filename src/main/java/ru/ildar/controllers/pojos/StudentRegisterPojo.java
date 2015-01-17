package ru.ildar.controllers.pojos;

public class StudentRegisterPojo
{
    private String username;
    private String password;
    private String repeatPassword;
    private String role;
    private String citySelect;
    private String uniSelect;
    private String facSelect;
    private String groupSelect;

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

    public String getCitySelect()
    {
        return citySelect;
    }

    public void setCitySelect(String citySelect)
    {
        this.citySelect = citySelect;
    }

    public String getUniSelect()
    {
        return uniSelect;
    }

    public void setUniSelect(String uniSelect)
    {
        this.uniSelect = uniSelect;
    }

    public String getFacSelect()
    {
        return facSelect;
    }

    public void setFacSelect(String facSelect)
    {
        this.facSelect = facSelect;
    }

    public String getGroupSelect()
    {
        return groupSelect;
    }

    public void setGroupSelect(String groupSelect)
    {
        this.groupSelect = groupSelect;
    }
}
