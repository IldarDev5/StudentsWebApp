package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ildar.database.entities.Person;
import ru.ildar.services.PersonService;
import ru.ildar.services.PictureService;

import java.security.Principal;

@Controller
@RequestMapping("/info")
public class InfoController
{
    @Autowired
    private PictureService pictureService;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "removePic", method = RequestMethod.POST)
    public String removeAvatar(@RequestParam("username") String username)
    {
        pictureService.removeAvatar(username);
        return "redirect:/info";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String info(Principal principal)
    {
        Person person = personService.getByUserName(principal.getName());
        switch (person.getRoleName())
        {
            case "ROLE_TEACHER":
                return "redirect:/info/teacher";
            case "ROLE_STUDENT":
                return "redirect:/info/student";
            default:
                return "redirect:/mainPage";
        }
    }
}
