package ru.ildar.services.impl.jpa;

import com.mysema.query.types.expr.BooleanExpression;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.UniDescriptionDAO;
import ru.ildar.database.repositories.UniversityDAO;
import ru.ildar.services.UniversityService;
import ru.ildar.services.factory.impl.JpaServiceFactory;

import java.util.ArrayList;
import java.util.List;

import static ru.ildar.CollectionsUtil.getListFromIterable;

@Service
public class UniversityServiceJpaImpl implements UniversityService
{
    @Autowired
    private UniversityDAO universityDAO;
    @Autowired
    private UniDescriptionDAO uniDescriptionDAO;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public void addUniversity(University un)
    {
        universityDAO.save(un);
    }

    @Override
    public University getById(int id)
    {
        return universityDAO.findOne(id);
    }

    @Override
    public List<University> getUniversities(int pageNumber, int unisPerPage)
    {
        return getListFromIterable(universityDAO.findAll(new PageRequest(pageNumber, unisPerPage)));
    }

    @Override
    public int getPagesCount(int unisPerPage)
    {
        return (int)Math.ceil((double)universityDAO.count() / unisPerPage);
    }

    @Override
    public University getByIdWithFaculties(int unId)
    {
        University un = getById(unId);
        Hibernate.initialize(un.getFaculties());
        return un;
    }

    @Override
    public Iterable<University> getUniversitiesByCity(int cityId)
    {
        return universityDAO.findAll(QUniversity.university.city.id.eq(cityId));
    }

    @Override
    public void setCityAndAddUniversity(University university, int cityId)
    {
        QUniversity qUni = QUniversity.university;
        BooleanExpression expr = qUni.city.id.eq(cityId).and(qUni.unName.eq(university.getUnName()));
        University uni = universityDAO.findOne(expr);
        if(uni != null)
            throw new DuplicateKeyException("University with such name and city already exists.");

        City city = serviceFactory.getCityService().getById(cityId);
        university.setCity(city);

        universityDAO.save(university);
    }

    @Override
    public void removeUniversity(int unId)
    {
        universityDAO.delete(unId);
    }

    @Override
    public void setImage(int unId, byte[] bytes)
    {
        University university = universityDAO.findOne(unId);
        university.setUnImage(bytes);
    }

    @Override
    public UniversityDescription getDescription(int unId, String lang)
    {
        QUniversityDescription qUniD = QUniversityDescription.universityDescription;
        BooleanExpression expr = qUniD.university.unId.eq(unId).and(qUniD.language.eq(lang));
        return uniDescriptionDAO.findOne(expr);
    }

    @Override
    public void setUniversityDescription(UniversityDescription descr, String authorName)
    {
        Person changeAuthor = serviceFactory.getPersonService().getByUserName(authorName);
        descr.setLastChangePerson(changeAuthor);
        uniDescriptionDAO.save(descr);
    }

    @Override
    public UniversityDescription getDescriptionByLanguageAbbrev(int unId, String langAbbrev)
    {
        Language lang = serviceFactory.getLanguageService().getLanguageByAbbreviation(langAbbrev);
        QUniversityDescription qUniD = QUniversityDescription.universityDescription;
        BooleanExpression expr = qUniD.university.unId.eq(unId).and(qUniD.language.eq(lang.getLanguage()));
        return uniDescriptionDAO.findOne(expr);
    }

    @Override
    public UniversityDescription getFirstDescriptionForUniversity(int unId)
    {
        return uniDescriptionDAO.findOne(QUniversityDescription.universityDescription.university.unId.eq(unId));
    }

    @Override
    public void setCity(Integer unId, Integer cityId, String address)
    {
        University university = universityDAO.findOne(unId);
        university.setUnAddress(address);
        City city = serviceFactory.getCityService().getById(cityId);
        university.setCity(city);
    }
}
