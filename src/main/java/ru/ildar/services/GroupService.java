package ru.ildar.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    /**
     * Find a group by the specified group ID, initialize its
     * students collection, and return it
     * @param groupId ID of the group
     * @return
     */
    public Group getGroupWithStudents(String groupId)
    {
        Group group = groupDAO.findOne(groupId);
        Hibernate.initialize(group.getStudents());
        return group;
    }

    /**
     * Returns groups of the specified faculty
     * @param facultyId ID of the faculty
     */
    public Iterable<Group> getGroupsByFaculty(int facultyId)
    {
        return groupDAO.findByFaculty_FacultyIdOrderByGroupIdAscStudentsCountAsc(facultyId);
    }

    /**
     * Adds the specified group to the faculty
     * @param group Group to add
     * @param facultyId ID of the faculty
     */
    public void addGroupToFaculty(Group group, int facultyId)
    {
        Group gr = groupDAO.findOne(group.getGroupId());
        if(gr != null)
            throw new DuplicateKeyException("Group with such ID already exists.");

        Faculty faculty = facultyDAO.findOne(facultyId);
        group.setFaculty(faculty);
        groupDAO.save(group);
    }

    /**
     * Returns a group by its ID
     */
    public Object getGroupById(String groupId)
    {
        return groupDAO.findOne(groupId);
    }
}
