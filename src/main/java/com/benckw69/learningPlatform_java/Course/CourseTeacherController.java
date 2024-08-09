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
@RequestMapping("/teacher/course")
public class CourseTeacherController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    //check whether it is the owner of the course
    @GetMapping({"/{id}","/{id}/info/view"})
    public String viewCourse(@PathVariable Integer id, Model model, HttpSession httpSession){
        model.addAttribute("course", courseService.findCourseById(id));
        model.addAttribute("introduction", userService.getIntroductionBySession(httpSession).getIntrodution());
        return "pages/teacher_course_view";
    }

    @GetMapping("/{id}/info/edit")
    public String editCourseInfo(@PathVariable Integer id){
        return "pages/teacher_course_info";
    }

    @PostMapping("/{id}/info/edit")
    public String editCourseInfo(@PathVariable Integer id, BindingResult bindingResult){
        return "pages/teacher_course_info";
    }

    @GetMapping("/{id}/photo/edit")
    public String editCoursePhoto(@PathVariable Integer id){
        return "pages/teacher_course_info";
    }

    @PostMapping("/{id}/photo/edit")
    public String editCoursePhotoPost(@PathVariable Integer id){
        return "pages/teacher_course_info";
    }

    @GetMapping("/{id}/video/edit")
    public String editCourseVideo(@PathVariable Integer id){
        return "pages/teacher_course_info";
    }

    @PostMapping("/{id}/video/edit")
    public String editCourseVideoPost(@PathVariable Integer id){
        return "pages/teacher_course_info";
    }

    @GetMapping("/insert")
    public String insertCourse(Course course){
        return "pages/teacher_course_insert";
    }

    @PostMapping("/insert")
    public String insertCourse(@Valid Course course, BindingResult bindingResult,HttpSession httpSession){
        if(bindingResult.hasErrors()) return "pages/teacher_course_insert";
        User user = (User)httpSession.getAttribute("user");
        course.setUser(user);
        course = courseService.insertCourse(course);
        return "redirect:/teacher/course/"+course.getId();
    }
}
