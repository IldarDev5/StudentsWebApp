# Students and teachers Web application

## Brief description
This application provides the ability to work with entities in academic environment - students, teachers, universities and subjects.

## Business logic

### Tables
There are 17 tables in the database:
* CITIES - stores cities data - city name and country. Universities are connected to this table via foreign key in one-to-many relationship, that is, a university is located in some city. Each city must have at least one record in the CITIES_LOCALIZED table. The default localization is the name of the city and English language.
* CITIES_LOCALIZED - stores localized cities names - translations of city name in different locales. These translations are shown to teachers and students that have according locales. This table is also connected to CITIES table via one-to-many relationship.
* UNIVERSITIES - stores universities data - its name, city of location, foundation date etc.
* UN_DESCRIPTION - stores universities descriptions in various locales. University description is a text describing the university.
* PEOPLE - stores information about registered people of all roles - ADMIN, TEACHER and STUDENT. You can't register admin account via application, but you can register teachers and students. Students and teachers have to have their details in STUDENTS and TEACHERS tables respectively.
* STUDENTS - stores information about students accounts. Connected to PEOPLE table with one-to-one relationship via username that is a primary key in both tables. Students usually pertain to some academic group.
* TEACHERS - stores information about teachers accounts. Connected to PEOPLE table with one-to-one relationship via username that is a primary key in both tables. Teachers usually pertain to some university.
* FACULTIES - stores faculties. Each faculty belongs to some university. One university can have many faculties.
* GROUPS - stores academic groups. Groups are connected to the FACULTIES table via one-to-many relationship. Each group must pertain to some faculty. Group IDs are unique for all universities and cities.
* LANGUAGES - stores languages names and their abbreviations, for example English-en. Needed for cities and subjects localization purposes.
* ROLES - stores roles available in the application. At the moment application supports only three roles - ROLE_ADMIN, ROLE_TEACHER and ROLE_STUDENT.
* SUBJECT_TYPES - stores types of subjects, for example formal subjects, social subjects etc. Each subject must pertain to some type.
* SUBJECTS - stores information about subjects. Subjects are studied by academic groups, they can have localizations, each subject can be taught by more than one teacher, each teacher can teach more that one subject.
* SUBJECTS_LOCALIZED - stores subjects localizations. These translated subjects names will be shown to teachers and students who have appropriate locale set.
* NEWS - stores news. Each news has a brief description that is shown in the panel on the right of the page, and a full description that is visible when clicking "More info". Only administrators can create, modify, and delete news. Every admin can delete any news, but admin can only modify news created by himself.
* TEACHERS_GROUPS - stores information that shows - what teachers teach subjects and what groups do they teach them, and what semester this is.
* GRADES - stores information about grades. Grade is set by teacher for some subject to some student that is from the group that this teacher teaches.

### Roles privileges
Administrator:
* Can perform CRUD operations with cities, universities, faculties, subjects, groups;
* Can localize subjects and cities;
* Can add, update(with limitations) and delete news;
* Can add descriptions to the universities in various locales;
* Can change cities of universities;
* Can set images to universities.

Teacher:
* Can set information about himself, including university of teaching;
* Can upload an avatar and delete existing one;
* Can view students in the groups he teaches in;
* Can set grades to students he teaches.

Student:
* Can set information about himself;
* Can upload an avatar and delete existing one;
* Can view his own grades that teachers have set him;
* Can view his own teachers. Teacher of the student is the same as a teacher of the group student studies in.

News reading and viewing universities descriptions is available to anyone.

### Registration
Teachers and students account can be registered. When registering, student needs to select a city, university, faculty, and a group. Teacher needs to choose only his city and university. Teacher can further change his choice in the info page. Student can't change his study location by himself. Neither teacher, not student can delete his account from the system.

### Foreign key connection issues
Administrator can remove cities, universities, faculties, subjects, and groups. All these entities may have child records. This is how child records are handled:
* When a city is removed, its localizations are also removed. Universities that had this city set now don't have a city(foreign key field is set to null).
* When a university is removed, its teachers after that don't have a university(foreign key field is set to null), universities descriptions are removed, and faculties also don't have a university(foreign key field is set to null).
* When a faculty is removed, child groups are deleted.
* When a group is removed, its students don't have a group anymore(foreign key field is set to null). TEACHERS_GROUPS records that depend on this group record are left, with foreign key field set to null too.
* When subject is removed, grades from GRADES table and subject localizations are removed too.

## Technical details

This application uses Oracle Database and DDL and DML files are written specially for Oracle dialect. To use Oracle, you must have Oracle RDBMS installed on your PC and also you must install the Oracle JDBC driver into your local Maven repository. This is done via the following command:  
mvn install:install-file -Dfile=[path to file]\ojdbc6.jar -DgroupId=com.oracle -DartifactId=jdbc -Dversion=1.6 -Dpackaging=jar  
JDBC driver ojdbc6.jar goes with this application and is placed in the directory StudentsWebApp/libs.

## Used technologies
The following technologies and frameworks are used in this application:
* Java SE 1.8
* JPA/Hibernate ORM
* Hibernate validator
* Spring - Core, MVC, AOP, Data JPA, Security
* JavaScript, jQuery
* JSP, JSTL
* Apache Tiles
* Apache Commons Fileupload
* Jackson JSON mapper
* Querydsl