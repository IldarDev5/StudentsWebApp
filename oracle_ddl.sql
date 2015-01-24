create user students_app identified by ildar;
grant connect, resource to students_app;

create table students_app.cities(
  id int primary key,
  city_name varchar2(100) not null,
  country varchar2(100) not null,

  constraint uk_city_country unique(city_name, country)
);

create table STUDENTS_APP.languages(
  language varchar2(25) PRIMARY KEY,
  lang_abbrev varchar(5) not null unique
);

create table students_app.universities(
  un_id int primary key,
  un_name varchar2(150) not null,
  un_city_id int not null,
  un_image blob,
  teachers_count int default 0,

  constraint fk_city foreign key(un_city_id)
  references students_app.cities(id)
);

create table STUDENTS_APP.Un_Description(
  id int PRIMARY KEY,
  un_id int not null,
  description CLOB NOT NULL,
  last_change_date TIMESTAMP NOT NULL,
  last_change_person_username varchar(70) NOT NULL,
  language varchar2(25) not null,

  CONSTRAINT fk_university FOREIGN KEY(un_id)
  REFERENCES STUDENTS_APP.UNIVERSITIES(un_id),
  CONSTRAINT fk_change_person FOREIGN KEY(last_change_person_username)
  REFERENCES STUDENTS_APP.PEOPLE(USERNAME),
  CONSTRAINT fk_descr_language FOREIGN KEY(language)
  REFERENCES STUDENTS_APP.languages(language)
);

create table students_app.faculties(
  faculty_id int primary key,
  un_id int not null,
  faculty_name varchar2(150) not null,
  students_count int default 0,

  constraint fk_un foreign key(un_id)
  references students_app.universities(un_id)
);

create table students_app.roles(
  role_name varchar2(50) primary key
);

create table students_app.people(
  role_name varchar2(50) not null,
  username varchar2(70) primary key,
  password varchar2(70) not null,

  constraint fk_role_id foreign key(role_name)
  references students_app.roles(role_name)
);

create table students_app.subject_types(
  subject_type varchar2(50) primary key
);

create table students_app.subjects(
  subject_name varchar2(100) primary key,
  subject_type varchar2(50),

  constraint fk_subject_type foreign key (subject_type)
  references students_app.subject_types(subject_type)
);

create table students_app.groups(
  group_id varchar2(20) primary key,
  faculty_id int not null,
  students_count int default 0,

  constraint fk_faculty_group foreign key(faculty_id)
  references students_app.faculties(faculty_id)
);

create table students_app.students(
  username varchar2(70) primary key,
  first_name varchar2(100),
  last_name varchar2(100),
  email varchar2(100),
  group_id varchar2(20),
  enrollment date,
  person_photo blob,

  constraint fk_person_student foreign key(username)
  references students_app.people(username),
  constraint fk_person foreign key(group_id)
  references students_app.groups(group_id)
);

create table students_app.teachers(
  username varchar2(70) primary key,
  first_name varchar2(100),
  last_name varchar2(100),
  email varchar2(100),
  title varchar2(150),
  university_id int,
  work_start date,
  person_photo blob,

  constraint fk_person_teacher foreign key(username)
  references students_app.people(username),
  constraint fk_teacher_university foreign key(university_id)
    references students_app.universities(un_id)
);

create table students_app.teachers_groups(
  id int primary key,
  teacher_username varchar2(70) not null,
  group_id varchar2(20),
  subject_name varchar2(100) not null,
  semester int not null check (semester between 1 and 8),

  constraint uk_teachers_groups unique(teacher_username, group_id,
                                       subject_name, semester),
  constraint fk_tg_teacher foreign key(teacher_username)
  references students_app.teachers(username),
  constraint fk_tg_group foreign key(group_id)
  references students_app.groups(group_id),
  constraint fk_tg_subject_name foreign key(subject_name)
  references students_app.subjects(subject_name)
    on delete cascade
);

create table students_app.grades(
  grade_id int primary key,
  stud_username varchar2(70) not null,
  teacher_username varchar2(70) not null,
  grade_value int not null check(grade_value between 0 and 100),
  subject_name varchar2(100) not null,
  semester int not null check(semester between 1 and 8),

  constraint uk_grade unique(stud_username, teacher_username, subject_name, semester),

  constraint fk_gr_student foreign key(stud_username)
  references students_app.students(username),
  constraint fk_gr_teacher foreign key(teacher_username)
  references students_app.teachers(username),
  constraint fk_gr_subject foreign key(subject_name)
  references students_app.subjects(subject_name)
    on delete cascade
);


create or replace trigger students_app.faculty_person
before insert or update of group_id on students_app.students
for each row
  begin
    case
      when updating then
      begin
        update students_app.groups
        set students_count = students_count - 1
        where group_id = :OLD.group_id;

        update students_app.faculties
        set students_count = students_count - 1
        where faculty_id = (select faculty_id
                            from students_app.groups
                            where group_id = :OLD.group_id);
      end;
    else dbms_output.put_line('');
    end case;

    update students_app.groups
    set students_count = students_count + 1
    where group_id = :NEW.group_id;

    update students_app.faculties
    set students_count = students_count + 1
    where faculty_id = (select faculty_id
                        from students_app.groups
                        where group_id = :NEW.group_id);
  end;

create or replace trigger students_app.university_teacher
before insert or update of university_id on students_app.teachers
for each row
  begin
    case
      when updating then
        update students_app.universities
          set teachers_count = teachers_count - 1
          where un_id = :OLD.university_id;
      else
        dbms_output.put_line('');
    end case;

    update students_app.universities
      set teachers_count = teachers_count + 1
      where un_id = :NEW.university_id;
  end;

create or replace trigger students_app.remove_university
before delete on students_app.universities
for each row
  begin
    update students_app.teachers
      set university_id = null
      where university_id = :OLD.un_id;
  end;

create or replace trigger students_app.remove_group
before delete on students_app.groups
for each row
  begin
    update students_app.teachers_groups
      set group_id = null
      where group_id = :OLD.group_id;
    update students_app.students
      set group_id = null
      where group_id = :OLD.group_id;
  end;