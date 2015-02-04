package ru.ildar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    @Autowired
    private SubjectService subjectService;

    /**
     * Returns teachers of the specified subject.
     * The method searches, whether any of the teachers teaches
     * this subject some group, and collects all these teachers
     */
    public Set<Teacher> getTeachersBySubject(String subjectName)
    {
        List<TeachersGroups> lst = teachersGroupsDAO.findBySubjectName(subjectName);
        Set<Teacher> teachers = new TreeSet<>();
        lst.forEach((tg) -> teachers.add(tg.getTeacher()));
        return teachers;
    }

    /**
     * Rounding method for amount of pages
     */
    public int getTeachersPagesCount(int teachersPerPage)
    {
        return (int)Math.ceil((double)teacherDAO.count() / teachersPerPage);
    }

    /**
     * Returns teachers of the specified student and subjects that each teacher
     * teaches him. Teacher of the student = teacher of the group this student is in.
     */
    public Map<Teacher, Set<String>> getStudentTeachers(String studName, Locale locale)
    {
        Map<Teacher, Set<String>> result = new TreeMap<>();
        Student student = studentDAO.findOne(studName);
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
                        LocalizedSubject locSubj = subjectService
                                .getSubjectLocalization(tgr.getSubjectName(), locale.getLanguage());
                        subjs.add(locSubj != null ? locSubj.getSubjectTranslation() : tgr.getSubjectName());
                    });
            result.put(tg.getTeacher(), subjs);
        }

        return result;
    }

    /**
     * Returns teachers on the specified page
     * @param pageNumber Number of the page
     * @param teachersPerPage Amount of teachers per paga
     */
    public List<Teacher> getTeachers(int pageNumber, int teachersPerPage)
    {
        Iterable<Teacher> teachers = teacherDAO.findAll(new PageRequest(pageNumber, teachersPerPage));
        List<Teacher> result = new ArrayList<>();
        teachers.forEach(result::add);
        return result;
    }

    /**
     * Returns a photo of the teacher specified by his username
     */
    public byte[] getTeacherPhoto(String username)
    {
        return teacherDAO.findOne(username).getPersonPhoto();
    }

    /**
     * Returns the teacher specified by his username
     */
    public Teacher getByUserName(String username)
    {
        return teacherDAO.findOne(username);
    }

    /**
     * Assigns the photo and the university to the teacher instance
     * and updates it
     * @param details Teacher instance the values of which would be used for updating
     * @param unId ID of the university
     */
    public void setUniversityAndPhotoAndUpdate(Teacher details, int unId)
    {
        byte[] photo = getTeacherPhoto(details.getUsername());
        University un = universityDAO.findOne(unId);
        details.setPersonPhoto(photo);
        details.setUniversity(un);

        teacherDAO.save(details);
    }

    /**
     * Sets the university to the teacher and updates it
     * @param teacher Teacher instance the values of which would be used for updating
     * @param uniId ID of the university
     */
    public void setUniversityAndAddTeacher(Teacher teacher, int uniId)
    {
        University university = universityDAO.findOne(uniId);
        teacher.setUniversity(university);
        teacherDAO.save(teacher);
    }

    /**
     * Get groups that this teachers teaches, no matter what subjects
     */
    public List<TeachersGroups> getTeachersGroups(String name)
    {
        Sort sort = new Sort(Sort.Direction.ASC, "group_GroupId");
        sort = sort.and(new Sort(Sort.Direction.ASC, "semester"));
        return teachersGroupsDAO.findByTeacher_Username(name, sort);
    }

    /**
     * Returns the TeachersGroups instance specified by its ID
     */
    public TeachersGroups getTeachersGroupsById(int id)
    {
        return teachersGroupsDAO.findOne(id);
    }

    /**
     * Get TeachersGroups instances specified by three fields - subject, name, and student username
     */
    public TeachersGroups getTeachersGroupsBySubjectSemesterAndGroupStudent(String subject, int semester,
                                                 String studentSelect, String teacherUsername)
    {
        Student student = studentDAO.findOne(studentSelect);
        return teachersGroupsDAO.findBySubjectNameAndSemesterAndGroupAndTeacher_Username
                (subject, semester, student.getGroup(), teacherUsername);
    }

    /**
     * Add TeachersGroups instance to the database, but before
     * set group and teacher to it
     * @param tg Instance to save
     * @param groupId ID of the group
     * @param teacherUsername Username of the teacher
     */
    public void setGroupAndTeacherAndAddTeachersGroups(TeachersGroups tg,
                                                       String groupId, String teacherUsername)
    {
        Group group = groupDAO.findOne(groupId);
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

    /**
     * Get all teachers of the university specified by its ID
     */
    public List<Teacher> getTeachersByUniversity(int uniId)
    {
        return teacherDAO.findByUniversity_UnId(uniId);
    }

    /** Remove TeachersGroups instance from the database by its ID */
    public void removeTeachersGroups(Integer tGroupId)
    {
        teachersGroupsDAO.delete(tGroupId);
    }

    public void setTranslationToSubjects(List<TeachersGroups> tGroups, String languageAbbrev)
    {
        tGroups.forEach((tGroup) ->
        {
            LocalizedSubject locSubj = subjectService
                    .getSubjectLocalization(tGroup.getSubjectName(), languageAbbrev);
            if(locSubj != null)
                tGroup.setSubjectTranslation(locSubj.getSubjectTranslation());
        });
    }
}
