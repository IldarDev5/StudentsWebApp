package ru.ildar.controllers.student;

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
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.config.StudentRegisterPojo;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.Student;
import ru.ildar.services.PersonService;
import ru.ildar.services.StudentService;

import java.util.Arrays;

@Controller
public class StudentRegisterController
{
    @Autowired
    private StudentService studentService;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/register/student", method = RequestMethod.GET)
    public ModelAndView registerPage()
    {
        StudentRegisterPojo pojo = new StudentRegisterPojo();
        pojo.setRole("ROLE_STUDENT");
        return new ModelAndView("registerStudent", "student", pojo);
    }

    @RequestMapping(value = "/register/student", method = RequestMethod.POST)
    public ModelAndView registerPage(@ModelAttribute("student") StudentRegisterPojo stud)
    {
        if(!stud.getPassword().equals(stud.getRepeatPassword()))
            return regError("passNotEqual", stud);
        if(studentService.getByUserName(stud.getUsername()) != null)
            return regError("hasUsername", stud);

        stud.setPassword(new Md5PasswordEncoder().encodePassword(stud.getPassword(), null));
        Person person = new Person(stud.getUsername(), stud.getPassword(), stud.getRole());
        personService.addPerson(person);

        Student student = new Student();
        student.setUsername(stud.getUsername());
        studentService.setGroupAndAddStudent(student, stud.getGroupSelect());

        //authenticate user
        UserDetails authUser = new User(stud.getUsername(), stud.getPassword(),
                true, true, true, true, Arrays.asList(new SimpleGrantedAuthority(stud.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(authUser,
                null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/stud/info");
    }

    private ModelAndView regError(String attr, StudentRegisterPojo user)
    {
        ModelMap model = new ModelMap();
        model.addAttribute("user", user);
        model.addAttribute(attr, true);
        return new ModelAndView("registerStudent", model);
    }
}
