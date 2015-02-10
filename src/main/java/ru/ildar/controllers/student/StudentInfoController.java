package ru.ildar.controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.JsonStudentDetails;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.database.entities.Student;
import ru.ildar.services.CityService;
import ru.ildar.services.StudentService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Student controller that handles read and update operations on
 * information about students
 */
@Controller
@RequestMapping("/info/student")
public class StudentInfoController
{
    private StudentService studentService;
    private CityService cityService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        studentService = serviceFactory.getStudentService();
        cityService = serviceFactory.getCityService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal, ModelMap model, Locale locale)
    {
        if(username == null)
            username = principal.getName();

        Student student = studentService.getByUsername(username);
        if(student.getPersonPhoto() != null)
            //Set bytes size to 1 so JSP side would know that this student has an avatar
            student.setPersonPhoto(new byte[1]);

        //Adding city localization; if there's no localization of the current locale,
        //set default localization - US
        int cityId = student.getGroup().getFaculty().getUniversity().getCity().getId();
        LocalizedCity cityLoc = cityService.getLocalization(cityId, locale.getLanguage());
        if(cityLoc == null)
            cityLoc = cityService.getLocalization(cityId, Locale.US.getLanguage());

        model.addAttribute("cityLoc", cityLoc);
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
