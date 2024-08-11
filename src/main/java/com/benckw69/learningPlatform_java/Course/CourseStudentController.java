package com.benckw69.learningPlatform_java.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.benckw69.learningPlatform_java.User.User;
import com.benckw69.learningPlatform_java.User.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/student/course")
public class CourseStudentController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @GetMapping("/{courseId}")
    public String courseDetails(@PathVariable Integer courseId, Rating rating, Model model, HttpSession httpSession){
        //validate whether user can view the course
        Course course = courseService.getCourse(courseId);
        Boolean validation = courseService.validShowCourseToStudent(course,httpSession);
        if(course==null || !validation) return "redirect:/student/course/search";

        User user = (User)httpSession.getAttribute("user");
        model.addAttribute("paid", courseService.paid(user, course));
        model.addAttribute("course", course);
        model.addAttribute("introduction", userService.getIntroductionByCourseProducer(course).getIntrodution());
        return "pages/student_course_view";
    }

    @PostMapping("/{courseId}/buy")
    public String buyCourse(@PathVariable Integer courseId, HttpSession httpSession){
        //validate whether user can view the course
        Course course = courseService.getCourse(courseId);
        Boolean validation = courseService.validShowCourseToStudent(course,httpSession);
        if(course==null || !validation) return "redirect:/student/course/search";
        
        //check whether user have enough money to buy the course
        Boolean paid = courseService.buyCourse(course, httpSession);
        if(paid) return "redirect:/student/course/"+courseId+"?paid=true";
        else return "redirect:/student/course/"+courseId+"?paid=false";
    }

    @PostMapping("/{courseId}/rate")
    public String rateCourse(@PathVariable Integer courseId, @Valid Rating rating, BindingResult bindingResult, HttpSession httpSession){
        //validate whether user can view the course
        Course course = courseService.getCourse(courseId);
        Boolean validation = courseService.validShowCourseToStudent(course,httpSession);
        if(course==null || !validation) return "redirect:/student/course/search";
        if(bindingResult.hasErrors()) return "pages/student_course_view";
        return "redirect:/student/course/details?rate=true";
    }
}
