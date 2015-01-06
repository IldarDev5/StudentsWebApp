package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.University;
import ru.ildar.services.FacultyService;
import ru.ildar.services.UniversityService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class FacultiesController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private FacultyService facultyService;

    @RequestMapping(value = "/admin/faculties", method = RequestMethod.GET)
    public ModelAndView faculties(@RequestParam("un_id") int unId)
    {
        University u = universityService.getByIdWithFaculties(unId);
        return new ModelAndView("faculties", "uni", u);
    }

    @RequestMapping(value = "/admin/faculties/remove", method = RequestMethod.POST)
    public void removeFaculty(@RequestParam("facultyId") int facultyId)
    {

    }

    @RequestMapping(value = "/admin/faculties/add", method = RequestMethod.GET)
    public ModelAndView addNewFaculty(@RequestParam("unId") int uniId)
    {
        University un = universityService.getById(uniId);
        return new ModelAndView("addFaculty", "university", un);
    }

    @RequestMapping(value = "/admin/faculties/add", method = RequestMethod.POST)
    public ModelAndView addNewFaculty(@RequestParam("facultyName") String facultyName,
                                      @RequestParam("foundDate") String foundDate,
                                      @RequestParam("uni") String unId)
            throws ParseException
    {
        University un = universityService.getById(Integer.parseInt(unId));
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Faculty fac = new Faculty(facultyName, new Date(fmt.parse(foundDate).getTime()), un);

        facultyService.saveOrUpdateFaculty(fac);

        return new ModelAndView("redirect:/admin/faculties?un_id=" + unId);
    }
}
