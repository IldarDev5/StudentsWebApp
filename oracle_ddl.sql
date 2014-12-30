create user students_app identified by ildar;
grant connect, resource to students_app;

create table students_app.cities(
  id int primary key,
  city_name varchar2(100) not null,
  country varchar2(100) not null,

  constraint uk_city_country unique(city_name, country)
);

create table students_app.universities(
  un_id int primary key,
  un_name varchar2(150) not null,
  un_city_id int not null,
  un_image blob,

  constraint fk_city foreign key(un_city_id)
    references students_app.cities(id)
);

create table students_app.faculties(
  faculty_id int primary key,
  un_id int not null,
  faculty_name varchar2(150) not null,

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

create table students_app.people_details(
  username varchar2(70) primary key,
  first_name varchar2(100) not null,
  last_name varchar2(100) not null,
  email varchar2(100),
  faculty_id int not null,
  title varchar2(150),
  enrollment date,
  person_photo blob,

  constraint fk_people foreign key(username)
    references students_app.people(username),
  constraint fk_faculties foreign key(faculty_id)
    references students_app.faculties(faculty_id)
);

create table students_app.subjects(
  subject_name varchar2(100) primary key
);

create table students_app.grades(
  grade_id int primary key,
  stud_username varchar2(70) not null,
  teacher_username varchar2(70) not null,
  grade_value int not null check(grade_value between 0 and 100),
  subject_name varchar2(100) not null,
  semester int not null check(semester between 1 and 8),

  constraint uk_grade unique(stud_username, teacher_username, subject_name, semester),

  constraint fk_student foreign key(stud_username)
    references students_app.people(username),
  constraint fk_teacher foreign key(teacher_username)
    references students_app.people(username),
  constraint fk_subject foreign key(subject_name)
    references students_app.subjects(subject_name)
);

insert into students_app.roles(role_name) values ('ROLE_STUDENT');
insert into students_app.roles(role_name) values ('ROLE_TEACHER');
insert into students_app.roles(role_name) values ('ROLE_ADMIN');