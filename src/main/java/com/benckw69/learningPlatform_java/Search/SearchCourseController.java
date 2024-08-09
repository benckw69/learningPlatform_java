package com.benckw69.learningPlatform_java.Search;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchCourseController {

    @GetMapping("/student/course/search")
    public String viewAllCourses(SearchCourseRequest searchCourseRequest){
        return "/pages/student_course_search";
    }

    @PostMapping("/student/course/search")
    public String searchAllCourses(SearchCourseRequest searchCourseRequest){
        return "/pages/student_course_search";
    }

    @GetMapping("/student/course/own")
    public String viewOwnCourses(SearchCourseRequest searchCourseRequest){
        return "/pages/student_course_search";
    }

    @PostMapping("/student/course/own")
    public String searchOwnCourses(SearchCourseRequest searchCourseRequest){
        return "/pages/student_course_search";
    }

    @GetMapping("/teacher/course/own")
    public String ownCourses(SearchCourseRequest searchCourseRequest){
        return "/pages/teacher_own_courses";
    }

    @PostMapping("/teacher/course/own")
    public String SearchOwnCourses(SearchCourseRequest searchCourseRequest, Model model){
        return "/pages/teacher_own_courses";
    }
}
