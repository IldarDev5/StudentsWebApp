package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.StudentDAO;
import ru.ildar.database.repositories.TeacherDAO;
import ru.ildar.database.repositories.TeachersGroupsDAO;
import ru.ildar.database.repositories.UniversityDAO;

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

    public Set<Teacher> getTeachersBySubject(String subjectName)
    {
        List<TeachersGroups> lst = teachersGroupsDAO.findBySubjectName(subjectName);
        Set<Teacher> teachers = new TreeSet<>();
        lst.forEach((tg) -> teachers.add(tg.getTeacher()));
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
            tgs.stream().filter((tgr) -> tgr.getGroup().equals(group) &&
                    tgr.getTeacher().equals(tg.getTeacher()))
                    .forEach((tgr) -> subjs.add(tgr.getSubjectName()));
            result.put(tg.getTeacher(), subjs);
        }

        return result;
    }

    public List<Teacher> getTeachers(int pageNumber, int teachersPerPage)
    {
        Iterable<Teacher> teachers = teacherDAO.findAll(new PageRequest(pageNumber, teachersPerPage));
        List<Teacher> result = new ArrayList<>();
        teachers.forEach(result::add);
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
}
