package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.config.TeacherRegisterPojo;
import ru.ildar.database.entities.Person;
import ru.ildar.services.PersonService;

import java.util.Arrays;

@Controller
public class GuestController
{
    @RequestMapping(value = "/startPage", method = RequestMethod.GET)
    public String startPage()
    {
        return "startPage";
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage()
    {
        return "loginPage";
    }
}
