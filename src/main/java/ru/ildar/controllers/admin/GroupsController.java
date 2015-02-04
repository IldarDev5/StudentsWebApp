package ru.ildar.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.database.entities.Group;
import ru.ildar.services.CityService;
import ru.ildar.services.GroupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Administrator controller that handles CRUD operations with groups
 */
@Controller
@RequestMapping("/admin/groups")
public class GroupsController
{
    @Autowired
    private GroupService groupService;
    @Autowired
    private CityService cityService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView groups()
    {
        return new ModelAndView("groups", "cities", cityService.getAllCities());
    }

    @RequestMapping(value = "OfFaculty", method = RequestMethod.GET)
    @ResponseBody
    public List<Group> groupsOfFaculty(@RequestParam("facId") int facId)
    {
        Iterable<Group> groups = groupService.getGroupsByFaculty(facId);
        List<Group> result = new ArrayList<>();
        groups.forEach((g) -> { g.setStudents(null); g.getFaculty().setUniversity(null); result.add(g); });

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

    @RequestMapping(value = "add", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, String> addGroup(@RequestBody CreatedGroup group)
    {
        Map<String, String> result = new HashMap<>();
        if(group.getGroupId() == null || group.getGroupId().trim().equals(""))
        {
            result.put("ok", "false");
            result.put("reason", "EMPTY");
            return result;
        }

        try
        {
            groupService.addGroupToFaculty(new Group(group.getGroupId()), group.getFacId());
        }
        catch(DuplicateKeyException exc)
                //Group with such ID already exists in the database
        {
            result.put("ok", "false");
            result.put("reason", "DUPLICATE_NAME");
            return result;
        }

        result.put("ok", "true");
        return result;
    }
}
