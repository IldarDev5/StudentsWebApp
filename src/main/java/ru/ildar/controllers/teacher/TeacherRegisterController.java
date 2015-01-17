package ru.ildar.controllers.teacher;

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
import ru.ildar.controllers.pojos.TeacherRegisterPojo;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.Teacher;
import ru.ildar.services.PersonService;
import ru.ildar.services.TeacherService;

import java.util.Arrays;

@Controller
@RequestMapping("/register/teacher")
public class TeacherRegisterController
{
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView registerPage()
    {
        TeacherRegisterPojo teacherRegisterPojo = new TeacherRegisterPojo();
        teacherRegisterPojo.setRole("ROLE_TEACHER");
        return new ModelAndView("registerTeacher", "teacher", teacherRegisterPojo);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView registerPage(@ModelAttribute TeacherRegisterPojo teacherReg)
    {
        if(!teacherReg.getPassword().equals(teacherReg.getRepeatPassword()))
            return regError("passNotEqual", teacherReg);
        if(teacherService.getByUserName(teacherReg.getUsername()) != null)
            return regError("hasUsername", teacherReg);

        teacherReg.setPassword(new Md5PasswordEncoder().encodePassword(teacherReg.getPassword(), null));
        Person person = new Person(teacherReg.getUsername(), teacherReg.getPassword(), teacherReg.getRole());
        personService.addPerson(person);

        Teacher teacher = new Teacher();
        teacher.setUsername(teacherReg.getUsername());
        teacherService.setUniversityAndAddTeacher(teacher, teacherReg.getUniSelect());

        //authenticate user
        UserDetails authUser = new User(teacherReg.getUsername(), teacherReg.getPassword(),
                true, true, true, true, Arrays.asList(new SimpleGrantedAuthority(teacherReg.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(authUser,
                null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/info/teacher");
    }

    private ModelAndView regError(String attr, TeacherRegisterPojo user)
    {
        ModelMap model = new ModelMap();
        model.addAttribute("user", user);
        model.addAttribute(attr, true);
        return new ModelAndView("registerTeacher", model);
    }
}
