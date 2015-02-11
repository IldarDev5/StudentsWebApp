package ru.ildar.controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.JsonTeacherDetails;
import ru.ildar.database.entities.LocalizedCity;
import ru.ildar.database.entities.Teacher;
import ru.ildar.services.CityService;
import ru.ildar.services.TeacherService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

@Controller
@RequestMapping("/info/teacher")
public class TeacherInfoController
{
    private TeacherService teacherService;
    private CityService cityService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        teacherService = serviceFactory.getTeacherService();
        cityService = serviceFactory.getCityService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal, Locale locale, ModelMap model)
    {
        if(username == null)
            username = principal.getName();

        Teacher teacher = teacherService.getByUserName(username);
        if(teacher.getPersonPhoto() != null)
            //Set bytes size to 1 so JSP side would know that this teacher has an avatar
            teacher.setPersonPhoto(new byte[1]);

        //Adding city localization; if there's no localization of the current locale,
        //set default localization - US
        int cityId = teacher.getUniversity().getCity().getId();
        LocalizedCity cityLoc = cityService.getLocalization(cityId, locale.getLanguage());
        if(cityLoc == null)
            cityLoc = cityService.getLocalization(cityId, Locale.US.getLanguage());

        model.addAttribute("cityLoc", cityLoc);
        return new ModelAndView("teacher/info/info", "teacher", teacher);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json")
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
