package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.services.SubjectService;
import ru.ildar.services.UniversityService;

@Controller("/admin")
public class AdminController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/unis", method = RequestMethod.GET)
    public ModelAndView getUniversities()
    {
        return universities(0);
    }

    @RequestMapping(value = "/unis/{page_n}", method = RequestMethod.GET)
    public ModelAndView universities(@PathVariable("page_n") int pageNumber)
    {
        return null;
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ModelAndView subjects()
    {
        return null;
    }

    @RequestMapping(value = "/teachers", method = RequestMethod.GET)
    public ModelAndView teachers()
    {
        return null;
    }
}
