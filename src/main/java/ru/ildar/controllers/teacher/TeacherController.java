package ru.ildar.controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.ildar.config.TeacherRegisterPojo;
import ru.ildar.database.entities.*;
import ru.ildar.services.GradeService;
import ru.ildar.services.PersonService;
import ru.ildar.services.StudentService;
import ru.ildar.services.TeacherService;

import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Controller
public class TeacherController
{
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private PersonService personService;
    @Autowired
    private GradeService gradeService;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/info/teacher", method = RequestMethod.GET)
    public ModelAndView userInfo(@RequestParam(value = "username", required = false) String username,
                                 Principal principal)
    {
        if(username == null)
            username = principal.getName();

        Teacher teacher = teacherService.getByUserName(username);
        return new ModelAndView("teacherInfo", "teacher", teacher);
    }

    public static class JsonPersonDetails
    {
        private String firstName;
        private String lastName;
        private String email;
        private String workStart;
        private String title;
        private String unId;

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }

        public String getWorkStart()
        {
            return workStart;
        }

        public void setWorkStart(String workStart)
        {
            this.workStart = workStart;
        }

        public String getUnId()
        {
            return unId;
        }

        public void setUnId(String unId)
        {
            this.unId = unId;
        }
    }

    @RequestMapping(value = "/info/teacher", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public String userInfo(@RequestBody JsonPersonDetails pd, Principal principal)
            throws ParseException
    {
        int unId = Integer.parseInt(pd.getUnId());

        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        Date dt = pd.getWorkStart().equals("") ? null :
                new Date(fmt.parse(pd.getWorkStart()).getTime());

        String username = principal.getName();
        Teacher details = new Teacher(username, pd.getFirstName(), pd.getLastName(),
                pd.getEmail(), pd.getTitle(), null, dt, null);

        teacherService.setUniversityAndPhotoAndUpdate(details, unId);
        return "true";
    }

    @RequestMapping(value = "/register/teacher", method = RequestMethod.GET)
    public ModelAndView registerPage()
    {
        TeacherRegisterPojo teacherRegisterPojo = new TeacherRegisterPojo();
        teacherRegisterPojo.setRole("ROLE_TEACHER");
        return new ModelAndView("registerTeacher", "teacher", teacherRegisterPojo);
    }

    @RequestMapping(value = "/register/teacher", method = RequestMethod.POST)
    public ModelAndView registerPage(@ModelAttribute TeacherRegisterPojo teacherReg)
    {
        if(!teacherReg.getPassword().equals(teacherReg.getRepeatPassword()))
            return regError("passNotEqual", teacherReg);
        if(teacherService.getByUserName(teacherReg.getUsername()) != null)
            return regError("hasUsername", teacherReg);

        teacherReg.setPassword(new Md5PasswordEncoder().encodePassword(teacherReg.getPassword(), null));
        Person person = new Person(teacherReg.getUsername(), teacherReg.getPassword(), teacherReg.getRole());
        personService.addPerson(person);

        Teacher teacher = new Teacher();
        teacher.setUsername(teacherReg.getUsername());
        teacherService.setUniversityAndAddTeacher(teacher, teacherReg.getUniSelect());

        //authenticate user
        UserDetails authUser = new User(teacherReg.getUsername(), teacherReg.getPassword(),
                true, true, true, true, Arrays.asList(new SimpleGrantedAuthority(teacherReg.getRole())));
        Authentication auth = new UsernamePasswordAuthenticationToken(authUser,
                null, authUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return new ModelAndView("redirect:/teacher/info");
    }

    private ModelAndView regError(String attr, TeacherRegisterPojo user)
    {
        ModelMap model = new ModelMap();
        model.addAttribute("user", user);
        model.addAttribute(attr, true);
        return new ModelAndView("registerTeacher", model);
    }

    @RequestMapping(value = "/teacher/groups", method = RequestMethod.GET)
    public ModelAndView teacherGroups(Principal principal)
    {
        List<TeachersGroups> tGroups = teacherService.getTeachersGroups(principal.getName());
        return new ModelAndView("teacherGroups", "groups", tGroups);
    }

    @RequestMapping(value = "/teacher/grades", method = RequestMethod.GET)
    public ModelAndView gradesToGroupForSubjectBySemester(@RequestParam("id") int id, ModelMap model)
    {
        TeachersGroups tGroups = teacherService.getTeachersGroupsById(id);
        List<Grade> grades = gradeService.getByTeachersGroups(tGroups);

        model.addAttribute("tGroup", tGroups);
        model.addAttribute("grades", grades);

        return new ModelAndView("studentsGrades");
    }

    @RequestMapping(value = "/teacher/addGrade", method = RequestMethod.GET)
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

    @RequestMapping(value = "/teacher/checkStudentGrade", method = RequestMethod.GET)
    @ResponseBody
    public Long checkStudentGrade(@RequestParam("subject") String subject,
                                     @RequestParam("semester") int semester,
                                     @RequestParam("username") String username,
                                     Principal principal)
    {
        Grade grade = gradeService.getStudentGrade(subject, semester, username, principal.getName());
        return grade == null ? -1 : grade.getGradeValue();
    }

    @RequestMapping(value = "/teacher/grades/add", method = RequestMethod.POST)
    public ModelAndView addGrade(@ModelAttribute("studentPojo") StudentGradePojo s, Principal principal)
    {
        if(s.getGradeValue() < 0 || s.getGradeValue() > 100)
        {
            return new ModelAndView("redirect:/teacher/addGrade?subject=" + s.getSubject()
                        + "&groupId=" + s.getGroupId() + "&semester=" + s.getSemester());
        }

        Grade grade = new Grade(s.getGradeValue(), s.getSemester(), null, null, s.getSubject());
        gradeService.setStudentAndTeacherAndAddGrade(s.getStudentSelect(), principal.getName(), grade);

        TeachersGroups tGroups = teacherService.getTeachersGroupsBySubjectSemesterAndGroupStudent
                (s.getSubject(), s.getSemester(), s.getStudentSelect());
        return new ModelAndView("redirect:/teacher/grades?id=" + tGroups.getId());
    }
}
