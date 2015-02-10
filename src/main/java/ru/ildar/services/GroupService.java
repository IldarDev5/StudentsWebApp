package ru.ildar.services;

import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Group;

@Service
public interface GroupService
{
    /**
     * Find a group by the specified group ID, initialize its
     * students collection, and return it
     * @param groupId ID of the group
     */
    Group getGroupWithStudents(String groupId);

    /**
     * Returns groups of the specified faculty
     * @param facultyId ID of the faculty
     */
    Iterable<Group> getGroupsByFaculty(int facultyId);

    /**
     * Adds the specified group to the faculty
     * @param group Group to add
     * @param facultyId ID of the faculty
     */
    void addGroupToFaculty(Group group, int facultyId);

    /**
     * Returns a group by its ID
     */
    Group getGroupById(String groupId);
}
