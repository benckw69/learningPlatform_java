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

    //done
    @GetMapping("/student/course/search")
    public String viewAllCourses(SearchCourseRequest searchCourseRequest, Model model){
        List<Course> courses = courseService.findAllCourse();
        model.addAttribute("courses",courses);
        return "/pages/student_course_search";
    }

    @PostMapping("/student/course/search")
    public String searchAllCourses(SearchCourseRequest searchCourseRequest, Model model){
        searchCourseRequest.setSearchWords(searchCourseRequest.getSearchWords().trim());
        List<Course> courses = courseService.findAllCourseBySearch(searchCourseRequest);
        model.addAttribute("courses",courses);
        return "/pages/student_course_search";
    }

    //should be done.  need more testing
    @GetMapping("/student/course/own")
    public String viewOwnCourses(SearchCourseRequest searchCourseRequest, HttpSession httpSession, Model model){
        List<Course> courses = courseService.findOwnCourseByStudentId(httpSession);
        model.addAttribute("courses",courses);
        return "/pages/student_course_own";
    }

    //done.  need testing
    @PostMapping("/student/course/own")
    public String searchOwnCourses(SearchCourseRequest searchCourseRequest, HttpSession httpSession, Model model){
        searchCourseRequest.setSearchWords(searchCourseRequest.getSearchWords().trim());
        List<Course> courses = courseService.studentSearchOwnCourse(searchCourseRequest, httpSession);
        model.addAttribute("courses",courses);
        return "/pages/student_course_own";
    }

    @GetMapping("/teacher/course/own")
    public String ownCourses(SearchCourseRequest searchCourseRequest, Model model, HttpSession httpSession){
        List<Course> courses = courseService.findOwnCourseByTeacherId(httpSession);
        model.addAttribute("courses", courses);
        return "/pages/teacher_course_own";
    }

    @PostMapping("/teacher/course/own")
    public String SearchOwnCourses(SearchCourseRequest searchCourseRequest, Model model, HttpSession httpSession){
        List<Course> courses = courseService.teacherSearchOwnCourse(searchCourseRequest, httpSession);
        model.addAttribute("courses", courses);
        return "/pages/teacher_course_own";
    }
}
