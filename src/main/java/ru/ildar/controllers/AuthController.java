package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.CityDAO;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.database.repositories.UniversityDAO;
import ru.ildar.services.*;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/auth")
public class AuthController
{
    @Autowired
    private GroupService groupService;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "studentGroup", method = RequestMethod.GET)
    public ModelAndView studentGroup(@RequestParam("groupId") String groupId)
    {
        Group group = groupService.getGroupWithStudents(groupId);
        return new ModelAndView("groupStudents", "group", group);
    }
}
