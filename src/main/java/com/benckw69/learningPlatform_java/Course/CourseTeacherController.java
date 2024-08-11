package com.benckw69.learningPlatform_java.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.benckw69.learningPlatform_java.User.User;
import com.benckw69.learningPlatform_java.User.UserService;
import com.benckw69.learningPlatform_java.storage.FileType;
import com.benckw69.learningPlatform_java.storage.StorageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/teacher/course")
public class CourseTeacherController {

    @Autowired
    CourseService courseService;

    @Autowired
    UserService userService;

    @Autowired
    StorageService storageService;

    
    @GetMapping({"/{id}","/{id}/info/view"})
    public String viewCourse(@PathVariable Integer id, Model model, HttpSession httpSession){
        //check whether it is the owner of the course
        Course course = courseService.findCourseById(id);
        if(course == null || (course != null && course.getUser().getId() != (Integer)httpSession.getAttribute("userId"))) return "redirect:/teacher/course/own";
        model.addAttribute("course", course);
        model.addAttribute("introduction", userService.getIntroductionBySession(httpSession).getIntrodution());
        return "pages/teacher_course_view";
    }

    @GetMapping("/{id}/info/edit")
    public String editCourseInfo(@PathVariable Integer id, HttpSession httpSession, Course course, Model model){
        //check id and check whether it is the course owner
        Course validCourse = courseService.findCourseById(id);
        if(validCourse == null || (validCourse != null && validCourse.getUser().getId() != (Integer)httpSession.getAttribute("userId"))) return "redirect:/teacher/course/own";
        
        //set the column value
        course.setCategory(validCourse.getCategory());
        course.setContent(validCourse.getContent());
        course.setIntroduction(validCourse.getIntroduction());
        course.setPeopleSuite(validCourse.getPeopleSuite());
        course.setPrice(validCourse.getPrice());
        course.setTitle(validCourse.getTitle());
        model.addAttribute("course", validCourse);
        return "pages/teacher_course_info_edit";
    }

    @PostMapping("/{id}/info/edit")
    public String editCourseInfo(@PathVariable Integer id, @Valid Course course, Model model, HttpSession httpSession, BindingResult bindingResult){
        //check id and check whether it is the course owner
        Course validCourse = courseService.findCourseById(id);
        if(validCourse == null || (validCourse != null && validCourse.getUser().getId() != (Integer)httpSession.getAttribute("userId"))) return "redirect:/teacher/course/own";
        
        //check validation
        Boolean validation = validCourse.getTitle().equals(course.getTitle()) || !courseService.sameTitle(course.getTitle(), model);
        if(!validation || bindingResult.hasErrors()) return "pages/teacher_course_info_edit";
        
        //set new value
        courseService.editCourse(validCourse, course);
        return "redirect:/teacher/course/"+id+"/info/edit?edit=true";
    }

    @GetMapping("/{id}/photo/edit")
    public String editCoursePhoto(@PathVariable Integer id, Model model, HttpSession httpSession){
        //check id and check whether it is the course owner
        Course validCourse = courseService.findCourseById(id);
        if(validCourse == null || (validCourse != null && validCourse.getUser().getId() != (Integer)httpSession.getAttribute("userId"))) return "redirect:/teacher/course/own";
        model.addAttribute("course", validCourse);
        return "pages/teacher_course_photo_edit";
    }

    @PostMapping("/{id}/photo/edit")
    public String editCoursePhotoPost(@PathVariable Integer id, HttpSession httpSession, @RequestParam("file") MultipartFile file){
        //check id and check whether it is the course owner
        Course validCourse = courseService.findCourseById(id);
        if(validCourse == null || (validCourse != null && validCourse.getUser().getId() != (Integer)httpSession.getAttribute("userId"))) return "redirect:/teacher/course/own";
        Integer userId = (Integer)httpSession.getAttribute("userId");
        if(!file.isEmpty()){
            Boolean successful = storageService.store(file,FileType.PHOTO,userId+"");
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
            if(successful) courseService.update(validCourse,PhotoType.valueOf(extension));
            return successful? "redirect:/teacher/course/"+id+"/photo/edit?edit=true" : "redirect:/teacher/course/"+id+"/photo/edit?edit=false";
        } else {
            PhotoType photoType = null;
            courseService.update(validCourse,photoType);
            return "redirect:/teacher/course/"+id+"/photo/edit?edit=true";
        }
    }

    @GetMapping("/{id}/video/edit")
    public String editCourseVideo(@PathVariable Integer id, Model model, HttpSession httpSession){
        //check id and check whether it is the course owner
        Course validCourse = courseService.findCourseById(id);
        if(validCourse == null || (validCourse != null && validCourse.getUser().getId() != (Integer)httpSession.getAttribute("userId"))) return "redirect:/teacher/course/own";
        model.addAttribute("course", validCourse);
        return "pages/teacher_course_video_edit";
    }

    @PostMapping("/{id}/video/edit")
    public String editCourseVideoPost(@PathVariable Integer id, HttpSession httpSession, @RequestParam("file") MultipartFile file){
        //check id and check whether it is the course owner
        Course validCourse = courseService.findCourseById(id);
        if(validCourse == null || (validCourse != null && validCourse.getUser().getId() != (Integer)httpSession.getAttribute("userId"))) return "redirect:/teacher/course/own";
        Integer userId = (Integer)httpSession.getAttribute("userId");
        if(!file.isEmpty()){
            Boolean successful = storageService.store(file,FileType.VIDEO,userId+"");
            String fileName = file.getOriginalFilename();
            String extension = fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
            if(successful) courseService.update(validCourse,VideoType.valueOf(extension));
            return successful? "redirect:/teacher/course/"+id+"/video/edit?edit=true" : "redirect:/teacher/course/"+id+"/video/edit?edit=false";
        } else {
            VideoType videoType = null;
            courseService.update(validCourse,videoType);
            return "redirect:/teacher/course/"+id+"/video/edit?edit=true";
        }
        
    }

    @GetMapping("/insert")
    public String insertCourse(Course course){
        return "pages/teacher_course_insert";
    }

    @PostMapping("/insert")
    public String insertCourse(@Valid Course course, BindingResult bindingResult, Model model, HttpSession httpSession){
        Boolean validation = !courseService.sameTitle(course.getTitle(),model);
        if(bindingResult.hasErrors() || !validation) return "pages/teacher_course_insert";
        User user = (User)httpSession.getAttribute("user");
        course.setUser(user);
        course = courseService.insertCourse(course);
        return "redirect:/teacher/course/"+course.getId();
    }
}
