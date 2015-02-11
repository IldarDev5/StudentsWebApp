package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.*;
import ru.ildar.services.*;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;

/**
 * Controller that gives access to the information available
 * to any authenticated user
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController
{
    private GroupService groupService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        groupService = serviceFactory.getGroupService();
    }

    @RequestMapping(value = "studentGroup", method = RequestMethod.GET)
    public ModelAndView studentGroup(@RequestParam("groupId") String groupId)
    {
        Group group = groupService.getGroupWithStudents(groupId);
        return new ModelAndView("auth/group/group", "group", group);
    }
}
