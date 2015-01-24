package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ildar.database.entities.City;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.Group;
import ru.ildar.database.entities.University;
import ru.ildar.services.CityService;
import ru.ildar.services.FacultyService;
import ru.ildar.services.GroupService;
import ru.ildar.services.UniversityService;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller that handles AJAX requests that are
 * common for many pages to send
 */
@Controller
@RequestMapping("/ajax")
public class AjaxLoadingController
{
    @Autowired
    private GroupService groupService;
    @Autowired
    private CityService cityService;
    @Autowired
    private UniversityService universityService;
    @Autowired
    private FacultyService facultyService;


    public static class IdVal<I>
    {
        private I id;
        private String value;

        public IdVal(I id, String value)
        {
            this.id = id;
            this.value = value;
        }

        public I getId()
        {
            return id;
        }
        public String getValue()
        {
            return value;
        }
    }

    @RequestMapping(value = "cities", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> cities()
    {
        Iterable<City> cities = cityService.getAllCities();
        List<IdVal> result = new ArrayList<>();
        for(City city : cities)
            result.add(new IdVal<>(city.getId(), city.getCityName()));
        return result;
    }

    @RequestMapping(value = "universities", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> universities(@RequestParam("cityId") int cityId)
    {
        Iterable<University> universities = universityService.getUniversitiesByCity(cityId);
        List<IdVal> result = new ArrayList<>();
        for(University university : universities)
            result.add(new IdVal<>(university.getUnId(), university.getUnName()));
        return result;
    }

    @RequestMapping(value = "faculties", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> faculties(@RequestParam("uniId") int universityId)
    {
        Iterable<Faculty> faculties = facultyService.getFacultiesByUniversity(universityId);
        List<IdVal> result = new ArrayList<>();
        for(Faculty faculty : faculties)
            result.add(new IdVal<>(faculty.getFacultyId(), faculty.getFacultyName()));
        return result;
    }

    @RequestMapping(value = "groups", method = RequestMethod.GET)
    @ResponseBody
    public List<IdVal> groups(@RequestParam("facId") int facultyId)
    {
        Iterable<Group> groups = groupService.getGroupsByFaculty(facultyId);
        List<IdVal> result = new ArrayList<>();
        for(Group group : groups)
            result.add(new IdVal<>(group.getGroupId(), group.getGroupId()));
        return result;
    }
}
