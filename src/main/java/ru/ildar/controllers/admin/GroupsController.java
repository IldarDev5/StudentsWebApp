package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Group;
import ru.ildar.services.GroupService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupsController
{
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "/admin/groups", method = RequestMethod.GET)
    public ModelAndView groups()
    {
        return new ModelAndView("groups");
    }

    @RequestMapping(value = "/admin/groupsOfFaculty", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> groupsOfFaculty(@RequestParam("facId") int facId)
    {
        Iterable<Group> groups = groupService.getGroupsByFaculty(facId);
        List<Group> result = new ArrayList<>();
        groups.forEach((g) -> { g.setStudents(null); g.getFaculty().setUniversity(null); result.add(g); });

        return result;
    }

    @RequestMapping(value = "/admin/groups/add", method = RequestMethod.POST)
    @ResponseBody
    public String addGroup(@RequestParam("facId") int facultyId,
                           @RequestParam("groupId") String groupId)
    {
        groupService.addGroupToFaculty(new Group(groupId), facultyId);
        return "ok";
    }
}
