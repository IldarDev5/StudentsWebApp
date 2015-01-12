package ru.ildar.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.Group;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.database.repositories.GroupDAO;

@Service
public class GroupService
{
    @Autowired
    private GroupDAO groupDAO;
    @Autowired
    private FacultyDAO facultyDAO;

    public Group getGroupWithStudents(String groupId)
    {
        Group group = groupDAO.findOne(groupId);
        Hibernate.initialize(group.getStudents());
        return group;
    }

    public Iterable<Group> getGroupsByFaculty(int facultyId)
    {
        return groupDAO.findByFaculty_FacultyId(facultyId);
    }

    public void addGroupToFaculty(Group group, long facultyId)
    {
        Faculty faculty = facultyDAO.findOne(facultyId);
        group.setFaculty(faculty);
        groupDAO.save(group);
    }
}
