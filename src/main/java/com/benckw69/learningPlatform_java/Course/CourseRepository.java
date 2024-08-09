package com.benckw69.learningPlatform_java.Course;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    //check whether it has the title
    Course findByTitle(String title);

}
