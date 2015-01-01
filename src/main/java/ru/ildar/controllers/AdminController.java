package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.University;
import ru.ildar.services.SubjectService;
import ru.ildar.services.UniversityService;

import java.util.List;

@Controller
public class AdminController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/admin/unis", method = RequestMethod.GET)
    public ModelAndView getUniversities(ModelMap model)
    {
        return universities(0, model);
    }

    @RequestMapping(value = "/admin/unis/{page_n}", method = RequestMethod.GET)
    public ModelAndView universities(@PathVariable("page_n") int pageNumber, ModelMap model)
    {
        int UNIS_PER_PAGE = 25;
        List<University> universities = universityService.getUniversities(pageNumber, UNIS_PER_PAGE);
        int count = universityService.getPagesCount(UNIS_PER_PAGE);

        model.addAttribute("universities", universities);
        model.addAttribute("pagesCount", count);
        model.addAttribute("pageNumber", pageNumber);

        return new ModelAndView("unis");
    }

    @RequestMapping(value = "/admin/faculties", method = RequestMethod.GET)
    public ModelAndView faculties(ModelMap model)
    {
        return faculties(0, model);
    }

    @RequestMapping(value = "/admin/faculties/{page_n}", method = RequestMethod.GET)
    public ModelAndView faculties(@PathVariable("page_n") int pageNumber, ModelMap model)
    {
        return null;
    }

    @RequestMapping(value = "/admin/subjects", method = RequestMethod.GET)
    public ModelAndView subjects()
    {
        return null;
    }

    @RequestMapping(value = "/admin/teachers", method = RequestMethod.GET)
    public ModelAndView teachers()
    {
        return null;
    }
}
