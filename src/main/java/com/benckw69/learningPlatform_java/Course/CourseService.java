package com.benckw69.learningPlatform_java.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public Course insertCourse(Course course){
        return courseRepository.save(course);
    }

    public Boolean sameTitle(String title, Model model){
        Course course = courseRepository.findByTitle(title);
        return course == null? false : true;
    }

    public Course findCourseById(Integer Id){
        return courseRepository.findById(Id).orElse(null);
    }
}
