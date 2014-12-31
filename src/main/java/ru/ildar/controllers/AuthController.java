package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Person;
import ru.ildar.services.PersonService;

import java.security.Principal;

@Controller
public class AuthController
{
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/auth/info", method = RequestMethod.GET)
    public ModelAndView userInfo(Principal principal)
    {
        Person person = personService.getByUserName(principal.getName());
        return new ModelAndView("info", "user", person);
    }
}
