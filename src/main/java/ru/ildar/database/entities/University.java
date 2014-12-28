package ru.ildar.database.entities;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Ildar on 28.12.14.
 */
@Entity
@Table(name = "UNIVERSITIES", schema = "STUDENTS_APP", catalog = "")
public class University
{
    private long unId;
    private String unName;
    private String unAddress;
    private byte[] unImage;
    private Collection<Faculty> faculties;

    @Id
    @Column(name = "UN_ID")
    public long getUnId()
    {
        return unId;
    }

    public void setUnId(long unId)
    {
        this.unId = unId;
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

    @OneToMany(mappedBy = "university")
    public Collection<Faculty> getFaculties()
    {
        return faculties;
    }

    public void setFaculties(Collection<Faculty> faculties)
    {
        this.faculties = faculties;
    }
}
