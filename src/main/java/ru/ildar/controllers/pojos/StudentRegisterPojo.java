package ru.ildar.controllers.pojos;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class StudentRegisterPojo
{
    @NotNull
    @Size(min = 2, max = 70)
    private String username;

    @NotNull
    @Size(min = 2, max = 100)
    private String password;

    @NotNull
    @Size(min = 2, max = 100)
    private String repeatPassword;

    @NotNull
    @Size(min = 2, max = 15)
    private String role;

    @NotNull
    @Min(1)
    private Integer citySelect;

    @NotNull
    @Min(1)
    private Integer uniSelect;

    @NotNull
    @Min(1)
    private Integer facSelect;

    @NotNull
    @Size(min = 2, max = 20)
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

    public Integer getCitySelect()
    {
        return citySelect;
    }

    public void setCitySelect(Integer citySelect)
    {
        this.citySelect = citySelect;
    }

    public Integer getUniSelect()
    {
        return uniSelect;
    }

    public void setUniSelect(Integer uniSelect)
    {
        this.uniSelect = uniSelect;
    }

    public Integer getFacSelect()
    {
        return facSelect;
    }

    public void setFacSelect(Integer facSelect)
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
