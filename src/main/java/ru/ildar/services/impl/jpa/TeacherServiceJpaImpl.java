package ru.ildar.services.impl.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.ildar.database.entities.*;
import ru.ildar.database.repositories.TeacherDAO;
import ru.ildar.database.repositories.TeachersGroupsDAO;
import ru.ildar.services.*;
import ru.ildar.services.factory.impl.JpaServiceFactory;

import java.util.*;

@Service
public class TeacherServiceJpaImpl implements TeacherService
{
    @Autowired
    private TeacherDAO teacherDAO;
    @Autowired
    private TeachersGroupsDAO teachersGroupsDAO;

    @Autowired
    private JpaServiceFactory serviceFactory;

    @Override
    public Set<Teacher> getTeachersBySubject(String subjectName)
    {
        List<TeachersGroups> lst = teachersGroupsDAO.findBySubjectName(subjectName);
        Set<Teacher> teachers = new TreeSet<>();
        lst.forEach((tg) -> teachers.add(tg.getTeacher()));
        return teachers;
    }

    @Override
    public int getTeachersPagesCount(int teachersPerPage)
    {
        return (int)Math.ceil((double)teacherDAO.count() / teachersPerPage);
    }

    @Override
    public Map<Teacher, Set<String>> getStudentTeachers(String studName, Locale locale)
    {
        Map<Teacher, Set<String>> result = new TreeMap<>();
        Student student = serviceFactory.getStudentService().getByUsername(studName);
        Group group = student.getGroup();

        List<TeachersGroups> tgs = teachersGroupsDAO.findByGroup(group);
        for(TeachersGroups tg : tgs)
        {
            if(result.containsKey(tg.getTeacher()))
                continue;

            Set<String> subjs = new TreeSet<>();
            tgs.stream().filter((tgr) -> tgr.getTeacher().equals(tg.getTeacher()))
                    .forEach((tgr) ->
                    {
                        LocalizedSubject locSubj = serviceFactory.getSubjectService()
                                .getSubjectLocalization(tgr.getSubjectName(), locale.getLanguage());
                        subjs.add(locSubj != null ? locSubj.getSubjectTranslation() : tgr.getSubjectName());
                    });
            result.put(tg.getTeacher(), subjs);
        }

        return result;
    }

    @Override
    public List<Teacher> getTeachers(int pageNumber, int teachersPerPage)
    {
        Iterable<Teacher> teachers = teacherDAO.findAll(new PageRequest(pageNumber, teachersPerPage));
        List<Teacher> result = new ArrayList<>();
        teachers.forEach(result::add);
        return result;
    }

    @Override
    public byte[] getTeacherPhoto(String username)
    {
        return teacherDAO.findOne(username).getPersonPhoto();
    }

    @Override
    public Teacher getByUserName(String username)
    {
        return teacherDAO.findOne(username);
    }

    @Override
    public void setUniversityAndPhotoAndUpdate(Teacher details, int unId)
    {
        byte[] photo = getTeacherPhoto(details.getUsername());
        University un = serviceFactory.getUniversityService().getById(unId);
        details.setPersonPhoto(photo);
        details.setUniversity(un);

        teacherDAO.save(details);
    }

    @Override
    public void setUniversityAndAddTeacher(Teacher teacher, int uniId)
    {
        University university = serviceFactory.getUniversityService().getById(uniId);
        teacher.setUniversity(university);
        teacherDAO.save(teacher);
    }

    @Override
    public List<TeachersGroups> getTeachersGroups(String name)
    {
        Sort sort = new Sort(Sort.Direction.ASC, "group_GroupId");
        sort = sort.and(new Sort(Sort.Direction.ASC, "semester"));
        return teachersGroupsDAO.findByTeacher_Username(name, sort);
    }

    @Override
    public TeachersGroups getTeachersGroupsById(int id)
    {
        return teachersGroupsDAO.findOne(id);
    }

    @Override
    public TeachersGroups getTeachersGroupsBySubjectSemesterAndGroupStudent(String subject, int semester,
                                                                            String studentSelect, String teacherUsername)
    {
        Student student = serviceFactory.getStudentService().getByUsername(studentSelect);
        return teachersGroupsDAO.findBySubjectNameAndSemesterAndGroupAndTeacher_Username
                (subject, semester, student.getGroup(), teacherUsername);
    }

    @Override
    public void setGroupAndTeacherAndAddTeachersGroups(TeachersGroups tg,
                                                       String groupId, String teacherUsername)
    {
        Group group = serviceFactory.getGroupService().getGroupById(groupId);
        TeachersGroups otherTg = teachersGroupsDAO.findBySubjectNameAndSemesterAndGroupAndTeacher_Username
                (tg.getSubjectName(), tg.getSemester(), group, teacherUsername);
        if(otherTg != null)
        {
            throw new DuplicateKeyException("This group already has this teacher teaching this subject in this semester.");
        }


        Teacher teacher = teacherDAO.findOne(teacherUsername);
        tg.setGroup(group);
        tg.setTeacher(teacher);

        teachersGroupsDAO.save(tg);
    }

    @Override
    public List<Teacher> getTeachersByUniversity(int uniId)
    {
        return teacherDAO.findByUniversity_UnId(uniId);
    }

    @Override
    public void removeTeachersGroups(Integer tGroupId)
    {
        teachersGroupsDAO.delete(tGroupId);
    }

    @Override
    public void setTranslationToSubjects(List<TeachersGroups> tGroups, String languageAbbrev)
    {
        tGroups.forEach((tGroup) ->
        {
            LocalizedSubject locSubj = serviceFactory.getSubjectService()
                    .getSubjectLocalization(tGroup.getSubjectName(), languageAbbrev);
            if(locSubj != null)
                tGroup.setSubjectTranslation(locSubj.getSubjectTranslation());
        });
    }
}
