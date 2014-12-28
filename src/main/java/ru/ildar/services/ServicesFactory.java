package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ServicesFactory
{
    @Autowired
    private UniversityService universityService;
    @Autowired
    private PersonService personService;
    @Autowired
    private GradeService gradeService;

    public UniversityService getUniversityService()
    {
        return universityService;
    }

    public PersonService getPersonService()
    {
        return personService;
    }

    public GradeService getGradeService()
    {
        return gradeService;
    }
}
