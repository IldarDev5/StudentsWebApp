package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.ildar.database.entities.PersonDetails;
import ru.ildar.services.PersonService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class PictureController
{
    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/pictures/avatar", method = RequestMethod.GET)
    public void showAvatar(@RequestParam("username") String username,
                           HttpServletResponse servletResponse)
            throws IOException
    {
        byte[] avatar = personService.getByUserName(username).getDetails().getPersonPhoto();

        if(avatar != null)
        {
            servletResponse.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            servletResponse.getOutputStream().write(avatar);
        }
        else
            servletResponse.sendRedirect("/images/no_avatar.png");

        servletResponse.getOutputStream().close();
    }
}
