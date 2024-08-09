package com.benckw69.learningPlatform_java.Course;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/students/course")
public class CourseStudentController {

    @GetMapping("/details/{courseId}")
    public String courseDetails(Rating rating){
        return "pages/students_course_details";
    }

    @PostMapping("/details/{courseId}/buy")
    public String buyCourse(@PathVariable Integer courseId){
        return "pages/students_course_details";
        //return "redirect:/student/course/details?buy=true";
    }

    @PostMapping("/details/{courseId}/rate")
    public String rateCourse(@PathVariable Integer courseId, @Valid Rating rating, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "pages/students_course_details";
        return "redirect:/students/course/details?rate=true";
    }
}
