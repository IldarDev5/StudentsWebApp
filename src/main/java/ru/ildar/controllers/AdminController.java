package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.University;
import ru.ildar.services.SubjectService;
import ru.ildar.services.UniversityService;

import java.util.List;

@Controller("/admin")
public class AdminController
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private SubjectService subjectService;

    @RequestMapping("/unis")
    public ModelAndView getUniversities()
    {
        return universities(0);
    }

    @RequestMapping("/unis/{page_n}")
    public ModelAndView universities(@PathVariable("page_n") int pageNumber)
    {
        return null;
    }

    @RequestMapping("/subjects")
    public ModelAndView subjects()
    {
        return null;
    }

    @RequestMapping("/teachers")
    public ModelAndView teachers()
    {
        return null;
    }
}
