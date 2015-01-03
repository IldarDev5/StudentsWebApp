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
import ru.ildar.config.UserRegisterPojo;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.PersonDetails;
import ru.ildar.services.PersonService;

import java.util.Arrays;

@Controller
public class GuestController
{
    @Autowired
    private PersonService personService;

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

    @RequestMapping(value = "/registerPage", method = RequestMethod.GET)
    public ModelAndView registerPage(@RequestParam("reg") String regType)
    {
        UserRegisterPojo user = new UserRegisterPojo();
        if(regType.equals("stud"))
            user.setRole("ROLE_STUDENT");
        else if(regType.equals("teach"))
            user.setRole("ROLE_TEACHER");
        else { /* ... */ }

        return new ModelAndView("registerPage", "user", user);
    }

    @RequestMapping(value = "/registerPage", method = RequestMethod.POST)
    public ModelAndView registerPage(@ModelAttribute("user") UserRegisterPojo user)
    {
        if(!user.getPassword().equals(user.getRepeatPassword()))
            return regError("passNotEqual", user);
        if(personService.getByUserName(user.getUsername()) != null)
            return regError("hasUsername", user);

        user.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(), null));
        Person person = new Person(user.getUsername(), user.getPassword(), user.getRole());
        personService.addPerson(person);

        //authenticate user
        UserDetails authUser = new User(user.getUsername(), user.getPassword(),
                true, true, true, true, Arrays.asList(new SimpleGrantedAuthority(user.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(authUser,
                null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        PersonDetails details = new PersonDetails(user.getUsername());
        return new ModelAndView("info", "user", details);
    }

    @RequestMapping(value = "/userDetails", method = RequestMethod.POST)
    public String userDetails(@ModelAttribute("user") PersonDetails details)
    {
        personService.saveOrUpdatePersonDetails(details);
        return "redirect:/startPage";
    }

    private ModelAndView regError(String attr, UserRegisterPojo user)
    {
        ModelMap model = new ModelMap();
        model.addAttribute("user", user);
        model.addAttribute(attr, true);
        return new ModelAndView("registerPage", model);
    }
}
