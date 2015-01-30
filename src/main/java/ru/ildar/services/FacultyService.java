package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.repositories.FacultyDAO;

@Service
public class FacultyService
{
    @Autowired
    private FacultyDAO facultyDAO;

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

    /**
     * Returns faculties of the university, specified by the ID
     */
    public Iterable<Faculty> getFacultiesByUniversity(int universityId)
    {
        return facultyDAO.findByUniversity_UnId(universityId);
    }

    /**
     * Remove a faculty by its ID
     */
    public void removeFaculty(long facultyId)
    {
        facultyDAO.delete(facultyId);
    }

    public int getStudentsCount(Long unId)
    {
        return facultyDAO.sumByUniversity_UnId(unId);
    }
}
