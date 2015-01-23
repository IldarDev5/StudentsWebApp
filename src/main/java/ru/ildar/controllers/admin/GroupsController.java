package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Group;
import ru.ildar.services.GroupService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/groups")
public class GroupsController
{
    @Autowired
    private GroupService groupService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView groups()
    {
        return new ModelAndView("groups");
    }

    @RequestMapping(value = "OfFaculty", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> groupsOfFaculty(@RequestParam("facId") int facId)
    {
        Iterable<Group> groups = groupService.getGroupsByFaculty(facId);
        List<Group> result = new ArrayList<>();
//        groups.forEach((g) -> { g.setStudents(null); g.getFaculty().setUniversity(null); result.add(g); });
        for(Group group : groups)
        {
            group.setStudents(null);
            group.getFaculty().setUniversity(null);
            result.add(group);
        }

        return result;
    }

    private static class CreatedGroup
    {
        private int facId;
        private String groupId;

        public int getFacId()
        {
            return facId;
        }

        public void setFacId(int facId)
        {
            this.facId = facId;
        }

        public String getGroupId()
        {
            return groupId;
        }

        public void setGroupId(String groupId)
        {
            this.groupId = groupId;
        }
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public String addGroup(@RequestBody CreatedGroup group)
    {
        groupService.addGroupToFaculty(new Group(group.getGroupId()), group.getFacId());
        return "ok";
    }
}
