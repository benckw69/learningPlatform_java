package com.benckw69.learningPlatform_java;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class Web_CourseController {

    @GetMapping("/")
    public String coursesSearch(){
        return "pages/courses_all";
    }

    @PostMapping("/")
    public String filterCoursesSearch(){
        return "pages/courses_all";
    }

    @GetMapping("/paid")
    public String paidCoursesSearch(){
        return "pages/courses_paid";
    }

    @GetMapping("/own")
    public String ownCourses(){
        return "pages/courses_own";
    }

    @GetMapping("/own/{courseId}")
    public String ownCourseInformation(@PathVariable String courseId){
        return "pages/course_edit";
    }
}
