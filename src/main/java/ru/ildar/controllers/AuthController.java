package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.*;
import ru.ildar.services.*;

/**
 * Controller that gives access to the information available
 * to any authenticated user
 */
@Controller
@RequestMapping(value = "/auth")
public class AuthController
{
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "studentGroup", method = RequestMethod.GET)
    public ModelAndView studentGroup(@RequestParam("groupId") String groupId)
    {
        Group group = groupService.getGroupWithStudents(groupId);
        return new ModelAndView("groupStudents", "group", group);
    }
}
