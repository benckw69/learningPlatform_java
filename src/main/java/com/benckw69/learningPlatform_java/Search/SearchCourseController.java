package com.benckw69.learningPlatform_java.Search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.benckw69.learningPlatform_java.Course.Course;
import com.benckw69.learningPlatform_java.Course.CourseService;
import com.benckw69.learningPlatform_java.User.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class SearchCourseController {

    @Autowired
    CourseService courseService;

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
    public String ownCourses(SearchCourseRequest searchCourseRequest, Model model, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        List<Course> courses = courseService.findCourseByUserId(user);
        model.addAttribute("courses", courses);
        return "/pages/teacher_course_own";
    }

    @PostMapping("/teacher/course/own")
    public String SearchOwnCourses(SearchCourseRequest searchCourseRequest, Model model, HttpSession httpSession){
        List<Course> courses = courseService.teacherSearchCourse(searchCourseRequest, httpSession);
        model.addAttribute("courses", courses);
        return "/pages/teacher_course_own";
    }
}
