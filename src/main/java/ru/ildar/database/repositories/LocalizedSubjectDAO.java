package ru.ildar.database.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.ildar.database.entities.LocalizedSubject;

public interface LocalizedSubjectDAO extends CrudRepository<LocalizedSubject, Integer>
{
    LocalizedSubject findBySubject_SubjectNameAndLanguage_Abbreviation
            (String subjectName, String languageAbbrev);

}
