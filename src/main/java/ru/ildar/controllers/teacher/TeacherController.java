package ru.ildar.controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.StudentGradePojo;
import ru.ildar.database.entities.*;
import ru.ildar.services.GradeService;
import ru.ildar.services.StudentService;
import ru.ildar.services.TeacherService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/teacher")
public class TeacherController
{
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    public ModelAndView teacherGroups(Principal principal)
    {
        List<TeachersGroups> tGroups = teacherService.getTeachersGroups(principal.getName());
        return new ModelAndView("teacherGroups", "groups", tGroups);
    }

    @RequestMapping(value = "/grades", method = RequestMethod.GET)
    public ModelAndView gradesToGroupForSubjectBySemester(@RequestParam("id") int id, ModelMap model)
    {
        TeachersGroups tGroups = teacherService.getTeachersGroupsById(id);
        List<Grade> grades = gradeService.getByTeachersGroups(tGroups);

        model.addAttribute("tGroup", tGroups);
        model.addAttribute("grades", grades);

        return new ModelAndView("studentsGrades");
    }

    @RequestMapping(value = "/grades/add", method = RequestMethod.GET)
    public ModelAndView addGrade(@RequestParam("subject") String subject,
                                 @RequestParam("groupId") String groupId,
                                 @RequestParam("semester") int semester,
                                 ModelMap model)
    {
        List<Student> studs = studentService.getByGroup(groupId);

        model.addAttribute("subject", subject);
        model.addAttribute("groupId", groupId);
        model.addAttribute("semester", semester);
        model.addAttribute("students", studs);

        return new ModelAndView("addGrade");
    }

    @RequestMapping(value = "/checkStudentGrade", method = RequestMethod.GET)
    @ResponseBody
    public Long checkStudentGrade(@RequestParam("subject") String subject,
                                     @RequestParam("semester") int semester,
                                     @RequestParam("username") String username,
                                     Principal principal)
    {
        Grade grade = gradeService.getStudentGrade(subject, semester, username, principal.getName());
        return grade == null ? -1 : grade.getGradeValue();
    }

    @RequestMapping(value = "/grades/add", method = RequestMethod.POST)
    public ModelAndView addGrade(@ModelAttribute("studentPojo") StudentGradePojo s, Principal principal)
    {
        if(s.getGradeValue() < 0 || s.getGradeValue() > 100)
        {
            return new ModelAndView("redirect:/teacher/grades/add?subject=" + s.getSubject()
                        + "&groupId=" + s.getGroupId() + "&semester=" + s.getSemester());
        }

        Grade grade = new Grade(s.getGradeValue(), s.getSemester(), null, null, s.getSubject());
        gradeService.setStudentAndTeacherAndAddGrade(s.getStudentSelect(), principal.getName(), grade);

        TeachersGroups tGroups = teacherService.getTeachersGroupsBySubjectSemesterAndGroupStudent
                (s.getSubject(), s.getSemester(), s.getStudentSelect());
        return new ModelAndView("redirect:/teacher/grades?id=" + tGroups.getId());
    }
}
