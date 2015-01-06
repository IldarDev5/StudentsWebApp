package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.CityDAO;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.database.repositories.UniversityDAO;
import ru.ildar.services.CityService;
import ru.ildar.services.FacultyService;
import ru.ildar.services.PersonService;
import ru.ildar.services.UniversityService;

import java.io.IOException;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController
{
    @Autowired
    private PersonService personService;
    @Autowired
    private CityService cityService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private FacultyService facultyService;

    @RequestMapping(value = "/auth/info", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal)
    {
        if(username == null)
            username = principal.getName();

        Person person = personService.getByUserName(username);
        return new ModelAndView("info", "user", person);
    }

    public static class JsonPersonDetails
    {
        private String firstName;
        private String lastName;
        private String email;
        private String enrollment;
        private String title;
        private String facultyId;

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

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getFacultyId()
        {
            return facultyId;
        }

        public void setFacultyId(String facultyId)
        {
            this.facultyId = facultyId;
        }
    }

    @RequestMapping(value = "/auth/info", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userInfo(@RequestBody JsonPersonDetails pd, Principal principal)
            throws ParseException
    {
        int facultyId = Integer.parseInt(pd.getFacultyId());

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = pd.getEnrollment().equals("") ? null :
                new Date(fmt.parse(pd.getEnrollment()).getTime());

        String username = principal.getName();
        PersonDetails details = new PersonDetails(username, pd.getFirstName(), pd.getLastName(),
                    pd.getEmail(), pd.getTitle(), dt, null, null);

        personService.setFacultyAndUpdate(details, facultyId);
        return "true";
    }

    @RequestMapping(value = "/auth/avatar", method = RequestMethod.POST)
    public String uploadNewAvatar(@RequestParam("avatar")MultipartFile avatar, Principal principal)
            throws IOException
    {
        String username = principal.getName();
        if(!avatar.isEmpty())
        {
            byte[] image = avatar.getBytes();
            personService.setImage(username, image);
        }

        return "redirect:/auth/info";
    }

    public static class IdVal
    {
        private long id;
        private String value;

        public IdVal(long id, String value)
        {
            this.id = id;
            this.value = value;
        }

        public long getId()
        {
            return id;
        }
        public String getValue()
        {
            return value;
        }
    }

    @RequestMapping(value = "/auth/cities", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> cities()
    {
        Iterable<City> cities = cityService.getAllCities();
        List<IdVal> result = new ArrayList<>();
        for(City city : cities)
            result.add(new IdVal(city.getId(), city.getCityName()));
        return result;
    }

    @RequestMapping(value = "/auth/universities", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> universities(@RequestParam("cityId") int cityId)
    {
        Iterable<University> universities = universityService.getUniversitiesByCity(cityId);
        List<IdVal> result = new ArrayList<>();
        for(University university : universities)
            result.add(new IdVal(university.getUnId(), university.getUnName()));
        return result;
    }

    @RequestMapping(value = "/auth/faculties", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> faculties(@RequestParam("universityId") int universityId)
    {
        Iterable<Faculty> faculties = facultyService.getFacultiesByUniversity(universityId);
        List<IdVal> result = new ArrayList<>();
        for(Faculty faculty : faculties)
            result.add(new IdVal(faculty.getFacultyId(), faculty.getFacultyName()));
        return result;
    }
}
