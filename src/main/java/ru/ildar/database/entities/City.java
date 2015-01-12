package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "CITIES")
public class City
{
    private int id;
    private String cityName;
    private String country;

    public City() { }
    public City(String cityName, String country)
    {
        this.cityName = cityName;
        this.country = country;
    }

    @Override
    public String toString()
    {
        return cityName + ", " + country;
    }

    @Id
    @Column(name = "ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    @Column(name = "CITY_NAME")
    public String getCityName()
    {
        return cityName;
    }

    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }

    @Column(name = "COUNTRY")
    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }
}
