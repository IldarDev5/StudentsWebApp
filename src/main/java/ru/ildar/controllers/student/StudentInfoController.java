package ru.ildar.controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.JsonStudentDetails;
import ru.ildar.database.entities.Student;
import ru.ildar.services.StudentService;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Student controller that handles read and update operations on
 * information about students
 */
@Controller
@RequestMapping("/info/student")
public class StudentInfoController
{
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal)
    {
        if(username == null)
            username = principal.getName();

        Student student = studentService.getByUsername(username);
        return new ModelAndView("studInfo", "stud", student);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userInfo(@RequestBody JsonStudentDetails pd, Principal principal)
            throws ParseException
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = pd.getEnrollment().equals("") ? null :
                new Date(fmt.parse(pd.getEnrollment()).getTime());

        String username = principal.getName();
        Student details = new Student(username, pd.getFirstName(), pd.getLastName(),
                pd.getEmail(), dt, null, null);

        studentService.updateStudent(details);
        return "true";
    }
}
