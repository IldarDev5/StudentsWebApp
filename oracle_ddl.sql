CREATE USER STUDENTS_APP IDENTIFIED BY ildar;
GRANT CONNECT, RESOURCE TO STUDENTS_APP;

CREATE TABLE STUDENTS_APP.CITIES(
  id INT PRIMARY KEY,
  city_name VARCHAR2(100) NOT NULL,
  country VARCHAR2(100) NOT NULL,

  CONSTRAINT UK_city_country UNIQUE(city_name, country)
);

CREATE TABLE STUDENTS_APP.CITIES_LOCALIZED(
  id INT PRIMARY KEY,
  city_id INT NOT NULL,
  language VARCHAR2(25) NOT NULL,
  translated_name VARCHAR2(50) NOT NULL,

  CONSTRAINT fk_city_localize FOREIGN KEY (city_id)
  REFERENCES STUDENTS_APP.CITIES(id)
    ON DELETE CASCADE,
  CONSTRAINT fk_language FOREIGN KEY (language)
  REFERENCES STUDENTS_APP.LANGUAGES(language),
  CONSTRAINT UK_city_language UNIQUE (city_id, language)
);

CREATE TABLE STUDENTS_APP.LANGUAGES(
  language VARCHAR2(25) PRIMARY KEY,
  lang_abbrev varchar(5) NOT NULL UNIQUE
);

CREATE TABLE STUDENTS_APP.UNIVERSITIES(
  un_id INT PRIMARY KEY,
  un_name VARCHAR2(150) NOT NULL,
  un_address VARCHAR2(100),
  un_city_id INT,
  un_image BLOB,
  TEACHERS_count INT DEFAULT 0,

  CONSTRAINT fk_city FOREIGN KEY(un_city_id)
  REFERENCES STUDENTS_APP.CITIES(id),
  CONSTRAINT UK_name_city UNIQUE(un_name, un_city_id)
);

CREATE TABLE STUDENTS_APP.UN_DESCRIPTION(
  id INT PRIMARY KEY,
  un_id INT NOT NULL,
  description CLOB NOT NULL,
  last_change_date TIMESTAMP NOT NULL,
  last_change_person_username varchar(70) NOT NULL,
  language VARCHAR2(25) NOT NULL,

  CONSTRAINT fk_university FOREIGN KEY(un_id)
  REFERENCES STUDENTS_APP.UNIVERSITIES(un_id),
  CONSTRAINT fk_change_person FOREIGN KEY(last_change_person_username)
  REFERENCES STUDENTS_APP.PEOPLE(USERNAME),
  CONSTRAINT fk_descr_language FOREIGN KEY(language)
  REFERENCES STUDENTS_APP.LANGUAGES(language)
);

CREATE TABLE STUDENTS_APP.FACULTIES(
  faculty_id INT PRIMARY KEY,
  un_id INT NOT NULL,
  faculty_name VARCHAR2(150) NOT NULL,
  STUDENTS_count INT DEFAULT 0,
  found_date DATE,

  CONSTRAINT fk_un FOREIGN KEY(un_id)
  REFERENCES STUDENTS_APP.UNIVERSITIES(un_id),
  CONSTRAINT UK_faculty UNIQUE(un_id, faculty_name)
);

CREATE TABLE STUDENTS_APP.ROLES(
  role_name VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE STUDENTS_APP.PEOPLE(
  role_name VARCHAR2(50) NOT NULL,
  username VARCHAR2(70) PRIMARY KEY,
  password VARCHAR2(70) NOT NULL,

  CONSTRAINT fk_role_id FOREIGN KEY(role_name)
  REFERENCES STUDENTS_APP.ROLES(role_name)
);

CREATE TABLE STUDENTS_APP.SUBJECT_TYPES(
  subject_type VARCHAR2(50) PRIMARY KEY
);

CREATE TABLE STUDENTS_APP.SUBJECTS(
  subject_name VARCHAR2(100) PRIMARY KEY,
  subject_type VARCHAR2(50) NOT NULL,

  CONSTRAINT fk_subject_type FOREIGN KEY (subject_type)
  REFERENCES STUDENTS_APP.SUBJECT_TYPES(subject_type)
);

CREATE TABLE STUDENTS_APP.SUBJECTS_LOCALIZED(
  id INT PRIMARY KEY,
  subject_name VARCHAR2(100) NOT NULL,
  subject_translation VARCHAR2(100) NOT NULL,
  language VARCHAR2(25) NOT NULL,

  CONSTRAINT fk_i18n_subject_name FOREIGN KEY (subject_name)
  REFERENCES STUDENTS_APP.SUBJECTS(subject_name)
    ON DELETE CASCADE,
  CONSTRAINT fk_i8n_language FOREIGN KEY (language)
  REFERENCES STUDENTS_APP.LANGUAGES(language),
  CONSTRAINT UK_subject_LOCALIZED UNIQUE (subject_name, language)
);

CREATE TABLE STUDENTS_APP.GROUPS(
  group_id VARCHAR2(20) PRIMARY KEY,
  faculty_id INT NOT NULL,
  STUDENTS_count INT DEFAULT 0,

  CONSTRAINT fk_faculty_group FOREIGN KEY(faculty_id)
  REFERENCES STUDENTS_APP.FACULTIES(faculty_id)
);

CREATE TABLE STUDENTS_APP.STUDENTS(
  username VARCHAR2(70) PRIMARY KEY,
  first_name VARCHAR2(100),
  last_name VARCHAR2(100),
  email VARCHAR2(100),
  group_id VARCHAR2(20),
  enrollment DATE,
  person_photo BLOB,

  CONSTRAINT fk_person_student FOREIGN KEY(username)
  REFERENCES STUDENTS_APP.PEOPLE(username),
  CONSTRAINT fk_person FOREIGN KEY(group_id)
  REFERENCES STUDENTS_APP.GROUPS(group_id),
  CONSTRAINT UK_person UNIQUE(first_name, last_name, email, group_id)
);

CREATE TABLE STUDENTS_APP.TEACHERS(
  username VARCHAR2(70) PRIMARY KEY,
  first_name VARCHAR2(100),
  last_name VARCHAR2(100),
  email VARCHAR2(100),
  title VARCHAR2(150),
  university_id INT,
  work_start DATE,
  person_photo BLOB,

  CONSTRAINT fk_person_teacher FOREIGN KEY(username)
  REFERENCES STUDENTS_APP.PEOPLE(username),
  CONSTRAINT fk_teacher_university FOREIGN KEY(university_id)
    REFERENCES STUDENTS_APP.UNIVERSITIES(un_id)
);

CREATE TABLE STUDENTS_APP.TEACHERS_GROUPS(
  id INT PRIMARY KEY,
  teacher_username VARCHAR2(70) NOT NULL,
  group_id VARCHAR2(20),
  subject_name VARCHAR2(100) NOT NULL,
  semester INT NOT NULL CHECK (semester BETWEEN 1 AND 8),

  CONSTRAINT UK_TEACHERS_GROUPS UNIQUE(teacher_username, group_id,
                                       subject_name, semester),
  CONSTRAINT fk_tg_teacher FOREIGN KEY(teacher_username)
  REFERENCES STUDENTS_APP.TEACHERS(username),
  CONSTRAINT fk_tg_group FOREIGN KEY(group_id)
  REFERENCES STUDENTS_APP.GROUPS(group_id),
  CONSTRAINT fk_tg_subject_name FOREIGN KEY(subject_name)
  REFERENCES STUDENTS_APP.SUBJECTS(subject_name)
    ON DELETE CASCADE
);

CREATE TABLE STUDENTS_APP.GRADES(
  grade_id INT PRIMARY KEY,
  stud_username VARCHAR2(70) NOT NULL,
  teacher_username VARCHAR2(70) NOT NULL,
  grade_value INT NOT NULL CHECK(grade_value BETWEEN 0 AND 100),
  subject_name VARCHAR2(100) NOT NULL,
  semester INT NOT NULL CHECK(semester BETWEEN 1 AND 8),

  CONSTRAINT UK_grade UNIQUE(stud_username, teacher_username, subject_name, semester),

  CONSTRAINT fk_gr_student FOREIGN KEY(stud_username)
  REFERENCES STUDENTS_APP.STUDENTS(username),
  CONSTRAINT fk_gr_teacher FOREIGN KEY(teacher_username)
  REFERENCES STUDENTS_APP.TEACHERS(username),
  CONSTRAINT fk_gr_subject FOREIGN KEY(subject_name)
  REFERENCES STUDENTS_APP.SUBJECTS(subject_name)
    ON DELETE CASCADE
);

CREATE TABLE STUDENTS_APP.NEWS(
  NEWS_id INT PRIMARY KEY,
  author_username VARCHAR2(70) NOT NULL,
  publish_date TIMESTAMP NOT NULL,
  brief_description VARCHAR2(150) NOT NULL,
  full_description VARCHAR2(1500) NOT NULL,

  CONSTRAINT fk_NEWS_author FOREIGN KEY(author_username)
  REFERENCES STUDENTS_APP.PEOPLE(USERNAME)
);

--Trigger that fires when the student faculty he was studying in has been changed
CREATE OR REPLACE TRIGGER STUDENTS_APP.FACULTY_PERSON
BEFORE INSERT OR UPDATE OF group_id ON STUDENTS_APP.STUDENTS
FOR EACH ROW
  BEGIN
    CASE
      WHEN UPDATING THEN
      BEGIN
        UPDATE STUDENTS_APP.GROUPS
        SET STUDENTS_count = STUDENTS_count - 1
        WHERE group_id = :OLD.group_id;

        UPDATE STUDENTS_APP.FACULTIES
        SET STUDENTS_count = STUDENTS_count - 1
        WHERE faculty_id = (SELECT faculty_id
                            FROM STUDENTS_APP.GROUPS
                            WHERE group_id = :OLD.group_id);
      END;
    ELSE dbms_output.put_line('');
    END CASE;

    UPDATE STUDENTS_APP.GROUPS
    SET STUDENTS_count = STUDENTS_count + 1
    WHERE group_id = :NEW.group_id;

    UPDATE STUDENTS_APP.FACULTIES
    SET STUDENTS_count = STUDENTS_count + 1
    WHERE faculty_id = (SELECT faculty_id
                        FROM STUDENTS_APP.GROUPS
                        WHERE group_id = :NEW.group_id);
  END;

  --Trigger that fires when teacher changes his university
CREATE OR REPLACE TRIGGER STUDENTS_APP.UNIVERSITY_TEACHER
BEFORE INSERT OR UPDATE OF university_id ON STUDENTS_APP.TEACHERS
FOR EACH ROW
  BEGIN
    CASE
      WHEN UPDATING THEN
        UPDATE STUDENTS_APP.UNIVERSITIES
          SET TEACHERS_count = TEACHERS_count - 1
          WHERE un_id = :OLD.university_id;
      ELSE
        dbms_output.put_line('');
    END CASE;

    UPDATE STUDENTS_APP.UNIVERSITIES
      SET TEACHERS_count = TEACHERS_count + 1
      WHERE un_id = :NEW.university_id;
  END;

  --Trigger that fires when university is being removed
CREATE OR REPLACE TRIGGER STUDENTS_APP.REMOVE_UNIVERSITY
BEFORE DELETE ON STUDENTS_APP.UNIVERSITIES
FOR EACH ROW
  BEGIN
    UPDATE STUDENTS_APP.TEACHERS
      SET university_id = NULL
      WHERE university_id = :OLD.un_id;
  END;

  --Trigger that fires when group is being removed
CREATE OR REPLACE TRIGGER STUDENTS_APP.REMOVE_GROUP
BEFORE DELETE ON STUDENTS_APP.GROUPS
FOR EACH ROW
  BEGIN
    UPDATE STUDENTS_APP.TEACHERS_GROUPS
      SET group_id = NULL
      WHERE group_id = :OLD.group_id;
    UPDATE STUDENTS_APP.STUDENTS
      SET group_id = NULL
      WHERE group_id = :OLD.group_id;
  END;

  --Trigger that fires when city is being removed
CREATE OR REPLACE TRIGGER STUDENTS_APP.REMOVE_CITY
BEFORE DELETE ON STUDENTS_APP.CITIES
  FOR EACH ROW
  BEGIN
    UPDATE STUDENTS_APP.UNIVERSITIES
      SET un_city_id = NULL
      WHERE un_city_id = :OLD.id;
  END;