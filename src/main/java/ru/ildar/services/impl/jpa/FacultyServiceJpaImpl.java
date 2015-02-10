package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.services.FacultyService;

@Service
public class FacultyServiceJpaImpl implements FacultyService
{
    @Autowired
    private FacultyDAO facultyDAO;

    @Override
    public void saveOrUpdateFaculty(Faculty fac)
    {
        Faculty otherFac = facultyDAO.
                findByUniversity_UnIdAndFacultyName(fac.getUniversity().getUnId(), fac.getFacultyName());
        if(otherFac != null)
        {
            throw new DuplicateKeyException("Faculty with such name already exists in the specified university");
        }

        facultyDAO.save(fac);
    }

    @Override
    public Iterable<Faculty> getFacultiesByUniversity(int universityId)
    {
        return facultyDAO.findByUniversity_UnId(universityId);
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
