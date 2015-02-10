package ru.ildar.controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.controllers.pojos.StudentGradePojo;
import ru.ildar.database.entities.Grade;
import ru.ildar.database.entities.Student;
import ru.ildar.database.entities.TeachersGroups;
import ru.ildar.services.GradeService;
import ru.ildar.services.StudentService;
import ru.ildar.services.TeacherService;
import ru.ildar.services.factory.ServiceFactory;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Controller
@RequestMapping("/teacher/grades")
public class TeacherGradesController
{
    private GradeService gradeService;
    private StudentService studentService;
    private TeacherService teacherService;

    @Autowired
    private ServiceFactory serviceFactory;

    @PostConstruct
    private void construct()
    {
        gradeService = serviceFactory.getGradeService();
        studentService = serviceFactory.getStudentService();
        teacherService = serviceFactory.getTeacherService();
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView gradesToGroupForSubjectBySemester(@RequestParam("id") int id, ModelMap model,
                                                          Locale locale)
    {
        TeachersGroups tGroups = teacherService.getTeachersGroupsById(id);
        List<Grade> grades = gradeService.getGradesByTeachersGroups(tGroups);
        gradeService.setTranslationToGradeSubjects(grades, locale.getLanguage());

        model.addAttribute("tGroup", tGroups);
        model.addAttribute("grades", grades);

        return new ModelAndView("studentsGrades");
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public ModelAndView addGrade(@RequestParam("subject") String subject,
                                 @RequestParam("groupId") String groupId,
                                 @RequestParam("semester") int semester,
                                 ModelMap model)
    {
        StudentGradePojo s = new StudentGradePojo(groupId, subject, semester, null, null);
        return addGrade(s, model);
    }

    private ModelAndView addGrade(StudentGradePojo pojo, ModelMap model)
    {
        List<Student> studs = studentService.getByGroup(pojo.getGroupId());

        model.addAttribute("students", studs);
        return new ModelAndView("addGrade", "studGrade", pojo);
    }

    @RequestMapping(value = "checkStudentGrade", method = RequestMethod.GET)
    @ResponseBody
    public Long checkStudentGrade(@RequestParam("subject") String subject,
                                  @RequestParam("semester") int semester,
                                  @RequestParam("username") String username,
                                  Principal principal)
    {
        Grade grade = gradeService.getStudentGrade(subject, semester, username, principal.getName());
        return grade == null ? -1 : grade.getGradeValue();
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ModelAndView addGrade(@ModelAttribute("studGrade") @Valid StudentGradePojo s,
                                 BindingResult result, Principal principal, ModelMap model)
    {
        if(result.hasErrors())
        {
            return addGrade(s, model);
        }

        Grade grade = new Grade(s.getGradeValue(), s.getSemester(), null, null, s.getSubject());
        gradeService.setStudentAndTeacherAndAddGrade(s.getStudentSelect(), principal.getName(), grade);

        TeachersGroups tGroups = teacherService.getTeachersGroupsBySubjectSemesterAndGroupStudent
                (s.getSubject(), s.getSemester(), s.getStudentSelect(), principal.getName());
        return new ModelAndView("redirect:/teacher/grades?id=" + tGroups.getId());
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String removeGrade(@RequestParam("gradeId") int gradeId,
                              @RequestParam("tGroupsId") int tGroupsId)
    {
        gradeService.removeGrade(gradeId);
        return "redirect:/teacher/grades?id=" + tGroupsId;
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String updateGrade(@RequestBody StudentGradePojo gr, Principal principal)
    {
        Grade grade = new Grade(gr.getGradeValue(), gr.getSemester(), null, null, gr.getSubject());
        gradeService.setStudentAndTeacherAndAddGrade(gr.getStudentSelect(), principal.getName(), grade);
        return "ok";
    }
}
