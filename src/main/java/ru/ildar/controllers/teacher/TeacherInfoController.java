package ru.ildar.controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.JsonTeacherDetails;
import ru.ildar.database.entities.Teacher;
import ru.ildar.services.TeacherService;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
@RequestMapping("/info/teacher")
public class TeacherInfoController
{
    @Autowired
    private TeacherService teacherService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal)
    {
        if(username == null)
            username = principal.getName();

        Teacher teacher = teacherService.getByUserName(username);
        return new ModelAndView("teacherInfo", "teacher", teacher);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userInfo(@RequestBody JsonTeacherDetails pd, Principal principal)
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
}
