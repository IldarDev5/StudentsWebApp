package ru.ildar.controllers;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.ildar.services.factory.ServiceFactory;

import static org.mockito.Mockito.reset;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = { "classpath:dispatcher-servlet.xml", "classpath:test-config.xml" }
)
@WebAppConfiguration
public abstract class AbstractControllerTest
{
    protected MockMvc mockMvc;

    @Autowired
    @Qualifier("mockServiceFactory")
    protected ServiceFactory serviceFactory;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp()
    {
        mockReset();

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private void mockReset()
    {
        Object[] mocksToReset = {
                serviceFactory.getCityService(),
                serviceFactory.getSubjectService(),
                serviceFactory.getFacultyService(),
                serviceFactory.getLanguageService(),
                serviceFactory.getGradeService(),
                serviceFactory.getGroupService(),
                serviceFactory.getNewsService(),
                serviceFactory.getPersonService(),
                serviceFactory.getPictureService(),
                serviceFactory.getStudentService(),
                serviceFactory.getTeacherService(),
                serviceFactory.getUniversityService(),
                serviceFactory.getUserDetailsService()
        };

        reset(mocksToReset);
    }
}
