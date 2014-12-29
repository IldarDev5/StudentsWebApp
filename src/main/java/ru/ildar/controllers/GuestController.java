package ru.ildar.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GuestController
{
    @RequestMapping("/startPage")
    public String startPage()
    {
        return "startPage";
    }
}
