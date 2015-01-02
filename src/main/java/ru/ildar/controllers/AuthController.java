package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.CityDAO;
import ru.ildar.database.repositories.FacultyDAO;
import ru.ildar.database.repositories.UniversityDAO;
import ru.ildar.services.PersonService;

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
    private CityDAO cityDAO;
    @Autowired
    private UniversityDAO universityDAO;
    @Autowired
    private FacultyDAO facultyDAO;

    @RequestMapping(value = "/auth/info", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal)
    {
        if(username == null)
            username = principal.getName();

        Person person = personService.getByUserName(username);
        return new ModelAndView("info", "user", person);
    }

    @RequestMapping(value = "/auth/info", method = RequestMethod.POST)
    @ResponseBody
    public String userInfo(@RequestBody Map<String, String> map, Principal principal)
            throws ParseException
    {
        String firstName = map.get("firstName");
        String lastName = map.get("lastName");
        String email = map.get("email");
        String title = map.get("title");
        String enrollment = map.get("enrollment");
        int facultyId = Integer.parseInt(map.get("facultyId"));

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");

        String username = principal.getName();
        PersonDetails details = new PersonDetails(username, firstName, lastName, email,
                        title, new Date(fmt.parse(enrollment).getTime()), null, null);

        personService.setFacultyAndUpdate(details, facultyId);
        return "true";
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
        Iterable<City> cities = cityDAO.findAll();
        List<IdVal> result = new ArrayList<>();
        for(City city : cities)
            result.add(new IdVal(city.getId(), city.getCityName()));
        return result;
    }

    @RequestMapping(value = "/auth/universities", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> universities()
    {
        Iterable<University> universities = universityDAO.findAll();
        List<IdVal> result = new ArrayList<>();
        for(University university : universities)
            result.add(new IdVal(university.getUnId(), university.getUnName()));
        return result;
    }

    @RequestMapping(value = "/auth/faculties", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> faculties()
    {
        Iterable<Faculty> faculties = facultyDAO.findAll();
        List<IdVal> result = new ArrayList<>();
        for(Faculty faculty : faculties)
            result.add(new IdVal(faculty.getFacultyId(), faculty.getFacultyName()));
        return result;
    }
}
