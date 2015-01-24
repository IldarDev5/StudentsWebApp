package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
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
}
