# Learning Platform
Web application using Thymeleaf, Java Spring, MySQL

A learning platform for students to join the course, teacher to produce the course and admin to manage the website.
Similar as 'learningPlatform' in the repository, but using java spring as backend.

## Tech that is used
**Backend**
- Java
- Spring Boot
- MySQL
- Spring Security
- Spring validation

**Frontend**
- HTML
- Thymeleaf
- JavaScript
- CSS

## Features
For students:
* Create your own account by email login
* Edit personal information
* Choose from the courses.  Search the courses and view the introduction before buying
* Filter courses by name, category or course producer
* Enroll any courses you want to
* View all enrolled courses
* View the rating and leave a rating of the course
* Add money by using money ticket
* View money record

For teachers:
* Create your own account by email login
* Edit personal information
* Make a new course
* Edit a course
* View money separation
* View money record

For admin:
* Add money ticket
* Manage money tickets
* Manage deleted account.  Can recover the deleted account.
* Manage money seperation
* Manage referral bonus
* Edit personal information

## Features to be added
* Teachers: Request withdrawal of money
* Adding a page to contact the admin
* Google login
* Add footer

## Installation
To use this repository, [MySQL](https://dev.mysql.com/downloads/mysql/) has to be installed.
Database has to be created at your MySQL server first to run! If you want to run a local MySQL server, you can first create a database named as 'learningplatform' as the database url mentioned below.
Also, you have to install [Java 21](https://www.oracle.com/hk/java/technologies/downloads/#java21) to run the Java application.
Firstly, clone this repository
```
git clone https://www.github.com/benckw69/learningPlatform_java.git
```
Then, navigate to the project folder
```
cd learningPlatform_java 
```
After that, use IDE and load maven

There is an example file called .env.example at 'src/main/resources', change the name to .env, input mysql server details such as username, password and server url.
```
database_url=jdbc:mysql://localhost:3306/learningplatform
DATABASE_USERNAME=root
DATABASE_PASSWORD=
```
After all of this setups, you are able to run the server using IDE, or run the server by creating the jar using maven.

```
java -jar your_jar_name
```
The server run at [http://localhost:8080](http://localhost:8080).
(There is one default account for admin.  Admin's email address is admin@gmail.com, password is adminadmin.

