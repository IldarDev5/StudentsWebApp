package ru.ildar.controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Student;
import ru.ildar.services.StudentService;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class StudentInfoController
{
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/info/student", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal)
    {
        if(username == null)
            username = principal.getName();

        Student student = studentService.getByUsername(username);
        return new ModelAndView("studInfo", "stud", student);
    }

    public static class JsonPersonDetails
    {
        private String firstName;
        private String lastName;
        private String email;
        private String enrollment;

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

        public String getEnrollment()
        {
            return enrollment;
        }

        public void setEnrollment(String enrollment)
        {
            this.enrollment = enrollment;
        }
    }

    @RequestMapping(value = "/info/student", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userInfo(@RequestBody JsonPersonDetails pd, Principal principal)
            throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = pd.getEnrollment().equals("") ? null :
                new Date(fmt.parse(pd.getEnrollment()).getTime());

        String username = principal.getName();
        Student details = new Student(username, pd.getFirstName(), pd.getLastName(),
                pd.getEmail(), dt, null, null);

        studentService.setGroupAndPhotoAndUpdate(details);
        return "true";
    }
}
