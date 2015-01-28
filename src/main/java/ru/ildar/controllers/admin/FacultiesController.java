package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.FacultyPojo;
import ru.ildar.database.entities.Faculty;
import ru.ildar.database.entities.University;
import ru.ildar.services.FacultyService;
import ru.ildar.services.UniversityService;

import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Administrator controller that handles CRUD operations with faculties
 */
@Controller
@RequestMapping("/admin/faculties")
public class FacultiesController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private FacultyService facultyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView faculties(@RequestParam("un_id") int unId)
    {
        University u = universityService.getByIdWithFaculties(unId);
        return new ModelAndView("faculties", "uni", u);
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    @ResponseBody
    public String removeFaculty(@RequestParam("facultyId") int facultyId)
    {
        facultyService.removeFaculty(facultyId);
        return "ok";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addNewFaculty(@RequestParam("unId") long uniId, ModelMap model)
    {
        University un = universityService.getById(uniId);
        model.addAttribute("university", un);
        FacultyPojo pojo = new FacultyPojo();
        pojo.setUnId(uniId);
        return new ModelAndView("addFaculty", "faculty", pojo);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(fmt, false));
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addNewFaculty(@ModelAttribute("faculty") @Valid FacultyPojo pojo,
                                      BindingResult result, ModelMap model)
            throws ParseException
    {
        if(result.hasErrors())
        {
            University un = universityService.getById(pojo.getUnId());
            model.addAttribute("university", un);
            return new ModelAndView("addFaculty", "faculty", pojo);
        }

        University un = universityService.getById(pojo.getUnId());
        Faculty fac = new Faculty(pojo.getFacultyName(), new Date(pojo.getFoundDate().getTime()), un);

        facultyService.saveOrUpdateFaculty(fac);

        return new ModelAndView("redirect:/admin/faculties?un_id=" + pojo.getUnId());
    }
}
