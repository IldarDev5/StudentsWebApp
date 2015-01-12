package ru.ildar.database.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "UNIVERSITIES", schema = "STUDENTS_APP", catalog = "")
public class University
{
    private long unId;
    private String unName;
    private String unAddress;
    private byte[] unImage;
    private Collection<Faculty> faculties;
    private City city;
    private int teachersCount;

    public University() { }
    public University(String unName, String unAddress, City city, byte[] unImage)
    {
        this.unName = unName;
        this.unAddress = unAddress;
        this.unImage = unImage;
        this.city = city;
    }

    @Id
    @Column(name = "UN_ID")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public long getUnId()
    {
        return unId;
    }

    public void setUnId(long unId)
    {
        this.unId = unId;
    }

    @ManyToOne
    @JoinColumn(name = "UN_CITY_ID", referencedColumnName = "ID", nullable = false)
    public City getCity()
    {
        return city;
    }

    public void setCity(City city)
    {
        this.city = city;
    }

    @Column(name = "TEACHERS_COUNT")
    public int getTeachersCount()
    {
        return teachersCount;
    }

    public void setTeachersCount(int teachersCount)
    {
        this.teachersCount = teachersCount;
    }

    @Basic
    @Column(name = "UN_NAME")
    public String getUnName()
    {
        return unName;
    }

    public void setUnName(String unName)
    {
        this.unName = unName;
    }

    @Basic
    @Column(name = "UN_ADDRESS")
    public String getUnAddress()
    {
        return unAddress;
    }

    public void setUnAddress(String unAddress)
    {
        this.unAddress = unAddress;
    }

    @Basic
    @Column(name = "UN_IMAGE")
    public byte[] getUnImage()
    {
        return unImage;
    }

    public void setUnImage(byte[] unImage)
    {
        this.unImage = unImage;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        University that = (University) o;

        return unId == that.getUnId();
    }

    @Override
    public int hashCode()
    {
        return (int) (unId ^ (unId >>> 32));
    }

    @OneToMany(mappedBy = "university", cascade = CascadeType.REMOVE)
    public Collection<Faculty> getFaculties()
    {
        return faculties;
    }

    public void setFaculties(Collection<Faculty> faculties)
    {
        this.faculties = faculties;
    }
}
