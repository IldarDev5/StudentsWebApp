package ru.ildar.services.impl.jpa;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.QFaculty;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.services.FacultyService;

import javax.persistence.EntityManager;

@Service
public class FacultyServiceJpaImpl implements FacultyService
{
    @Autowired
    private FacultyDAO facultyDAO;

    @Override
    public void saveOrUpdateFaculty(Faculty fac)
    {
        QFaculty f = QFaculty.faculty;
        BooleanExpression expr = f.university.unId.eq(fac.getUniversity().getUnId())
                .and(f.facultyName.eq(fac.getFacultyName()));
        Faculty otherFac = facultyDAO.findOne(expr);
        if(otherFac != null)
        {
            throw new DuplicateKeyException("Faculty with such name already exists in the specified university");
        }

        facultyDAO.save(fac);
    }

    @Override
    public Iterable<Faculty> getFacultiesByUniversity(int universityId)
    {
        BooleanExpression expr = QFaculty.faculty.university.unId.eq(universityId);
        return facultyDAO.findAll(expr);
    }

    @Override
    public void removeFaculty(int facultyId)
    {
        facultyDAO.delete(facultyId);
    }

    @Override
    public int getStudentsCount(int unId)
    {
        return facultyDAO.sumByUniversity_UnId(unId);
    }

    @Override
    public Faculty get(int facultyId)
    {
        return facultyDAO.findOne(facultyId);
    }
}
