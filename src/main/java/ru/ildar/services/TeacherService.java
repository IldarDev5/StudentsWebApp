package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.*;

import java.util.*;

@Service
public class TeacherService
{
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private TeachersGroupsDAO teachersGroupsDAO;
    @Autowired
    private StudentDAO studentDAO;
    @Autowired
    private UniversityDAO universityDAO;
    @Autowired
    private GroupDAO groupDAO;

    public Set<Teacher> getTeachersBySubject(String subjectName)
    {
        List<TeachersGroups> lst = teachersGroupsDAO.findBySubjectName(subjectName);
        Set<Teacher> teachers = new TreeSet<>();
//        lst.forEach((tg) -> teachers.add(tg.getTeacher()));
        for(TeachersGroups tg : lst)
            teachers.add(tg.getTeacher());
        return teachers;
    }

    public int getTeachersPagesCount(int teachersPerPage)
    {
        return (int)Math.ceil((double)teacherDAO.count() / teachersPerPage);
    }

    public Map<Teacher, Set<String>> getStudentTeachers(String studName)
    {
        Map<Teacher, Set<String>> result = new TreeMap<>();
        Student student = studentDAO.findOne(studName);
        Group group = student.getGroup();
        List<TeachersGroups> tgs = teachersGroupsDAO.findByGroup(group);
        for(TeachersGroups tg : tgs)
        {
            Set<String> subjs = new TreeSet<>();
//            tgs.stream().filter((tgr) -> tgr.getGroup().equals(group) &&
//                    tgr.getTeacher().equals(tg.getTeacher()))
//                    .forEach((tgr) -> subjs.add(tgr.getSubjectName()));
            for(TeachersGroups tgr : tgs)
                if(tgr.getGroup().equals(group) && tgr.getTeacher().equals(tg.getTeacher()))
                    subjs.add(tgr.getSubjectName());
            result.put(tg.getTeacher(), subjs);
        }

        return result;
    }

    public List<Teacher> getTeachers(int pageNumber, int teachersPerPage)
    {
        Iterable<Teacher> teachers = teacherDAO.findAll(new PageRequest(pageNumber, teachersPerPage));
        List<Teacher> result = new ArrayList<>();
//        teachers.forEach(result::add);
        for(Teacher teacher : teachers)
            result.add(teacher);
        return result;
    }

    public byte[] getTeacherPhoto(String username)
    {
        return teacherDAO.findOne(username).getPersonPhoto();
    }

    public Teacher getByUserName(String username)
    {
        return teacherDAO.findOne(username);
    }

    public void setUniversityAndPhotoAndUpdate(Teacher details, long unId)
    {
        byte[] photo = getTeacherPhoto(details.getUsername());
        University un = universityDAO.findOne(unId);
        details.setPersonPhoto(photo);
        details.setUniversity(un);

        teacherDAO.save(details);
    }

    public void setUniversityAndAddTeacher(Teacher teacher, long uniId)
    {
        University university = universityDAO.findOne(uniId);
        teacher.setUniversity(university);
        teacherDAO.save(teacher);
    }

    public List<TeachersGroups> getTeachersGroups(String name)
    {
        Sort sort = new Sort(Sort.Direction.ASC, "group_GroupId");
        sort = sort.and(new Sort(Sort.Direction.ASC, "semester"));
        return teachersGroupsDAO.findByTeacher_Username(name, sort);
    }

    public TeachersGroups getTeachersGroupsById(int id)
    {
        return teachersGroupsDAO.findOne(id);
    }

    public TeachersGroups getTeachersGroupsBySubjectSemesterAndGroupStudent(String subject,
                                                        int semester, String studentSelect)
    {
        Student student = studentDAO.findOne(studentSelect);
        return teachersGroupsDAO.findBySubjectNameAndSemesterAndGroup(subject, semester, student.getGroup());
    }

    public void setGroupAndTeacherAndAddTeachersGroups(TeachersGroups tg,
                                                       String groupId, String teacherUsername)
    {
        Group group = groupDAO.findOne(groupId);
        Teacher teacher = teacherDAO.findOne(teacherUsername);
        tg.setGroup(group);
        tg.setTeacher(teacher);

        teachersGroupsDAO.save(tg);
    }

    public List<Teacher> getTeachersByUniversity(int uniId)
    {
        return teacherDAO.findByUniversity_UnId(uniId);
    }
}
