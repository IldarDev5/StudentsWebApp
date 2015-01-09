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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.config.TeacherRegisterPojo;
import ru.ildar.database.entities.Person;
import ru.ildar.database.entities.Teacher;
import ru.ildar.services.PersonService;
import ru.ildar.services.TeacherService;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Controller
public class TeacherController
{
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/teacher/info", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal)
    {
        if(username == null)
            username = principal.getName();

        Teacher teacher = teacherService.getByUserName(username);
        return new ModelAndView("teacherInfo", "teacher", teacher);
    }

    public static class JsonPersonDetails
    {
        private String firstName;
        private String lastName;
        private String email;
        private String workStart;
        private String title;
        private String unId;

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getWorkStart()
        {
            return workStart;
        }

        public void setWorkStart(String workStart)
        {
            this.workStart = workStart;
        }

        public String getUnId()
        {
            return unId;
        }

        public void setUnId(String unId)
        {
            this.unId = unId;
        }
    }

    @RequestMapping(value = "/teacher/info", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userInfo(@RequestBody JsonPersonDetails pd, Principal principal)
            throws ParseException
    {
        int unId = Integer.parseInt(pd.getUnId());

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = pd.getWorkStart().equals("") ? null :
                new Date(fmt.parse(pd.getWorkStart()).getTime());

        String username = principal.getName();
        Teacher details = new Teacher(username, pd.getFirstName(), pd.getLastName(),
                pd.getEmail(), pd.getTitle(), null, dt, null);

        teacherService.setUniversityAndPhotoAndUpdate(details, unId);
        return "true";
    }

    @RequestMapping(value = "/register/teacher", method = RequestMethod.GET)
    public ModelAndView registerPage()
    {
        TeacherRegisterPojo teacherRegisterPojo = new TeacherRegisterPojo();
        teacherRegisterPojo.setRole("ROLE_TEACHER");
        return new ModelAndView("registerTeacher", "teacher", teacherRegisterPojo);
    }

    @RequestMapping(value = "/register/teacher", method = RequestMethod.POST)
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
        teacherService.setUniversityAndAddTeacher(teacher, teacherReg.getUniId());

        //authenticate user
        UserDetails authUser = new User(teacherReg.getUsername(), teacherReg.getPassword(),
                true, true, true, true, Arrays.asList(new SimpleGrantedAuthority(teacherReg.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(authUser,
                null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/teacher/info");
    }

    private ModelAndView regError(String attr, TeacherRegisterPojo user)
    {
        ModelMap model = new ModelMap();
        model.addAttribute("user", user);
        model.addAttribute(attr, true);
        return new ModelAndView("registerTeacher", model);
    }
}
