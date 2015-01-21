package ru.ildar.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.services.PersonService;
import ru.ildar.services.PictureService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/pictures")
public class PictureController
{
    @Autowired
    private PictureService pictureService;

    @RequestMapping(value = "avatar", method = RequestMethod.GET)
    public void showAvatar(@RequestParam(value = "username", required = false) String username,
                           HttpServletResponse servletResponse, Principal principal)
            throws IOException
    {
        if(username == null || username.trim().equals(""))
        {
            if(principal.getName() != null)
                username = principal.getName();
            else
            {
                servletResponse.sendRedirect("/loginPage");
                return;
            }
        }

        byte[] avatar = pictureService.getPicture(username);

        if(avatar != null)
        {
            servletResponse.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            servletResponse.getOutputStream().write(avatar);
        }
        else
            servletResponse.sendRedirect("/images/no_avatar.png");

        servletResponse.getOutputStream().close();
    }

    @RequestMapping(value = "avatar", method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadAvatar(@RequestParam("avatar") MultipartFile file, Principal principal)
            throws IOException
    {
        if(!file.isEmpty())
        {
            byte[] bytes = file.getBytes();
            pictureService.setPhoto(bytes, principal.getName());
        }

        return "redirect:/info";
    }
}
