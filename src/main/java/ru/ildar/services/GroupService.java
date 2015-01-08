package ru.ildar.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.Group;
import ru.ildar.database.repositories.GroupDAO;

@Service
public class GroupService
{
    @Autowired
    private GroupDAO groupDAO;

    public Group getGroupWithStudents(String groupId)
    {
        Group group = groupDAO.findOne(groupId);
        Hibernate.initialize(group.getStudents());
        return group;
    }
}
