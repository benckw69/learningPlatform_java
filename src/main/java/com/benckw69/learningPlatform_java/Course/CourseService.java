package com.benckw69.learningPlatform_java.Course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.benckw69.learningPlatform_java.Search.SearchCourseMethod;
import com.benckw69.learningPlatform_java.Search.SearchCourseRequest;
import com.benckw69.learningPlatform_java.User.User;

import jakarta.servlet.http.HttpSession;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public Course insertCourse(Course course){
        return courseRepository.save(course);
    }

    public Boolean sameTitle(String title, Model model){
        Course course = courseRepository.findByTitle(title);
        if(course!=null) model.addAttribute("titleError", "標題已被使用");
        return course == null? false : true;
    }

    public Course findCourseById(Integer Id){
        return courseRepository.findById(Id).orElse(null);
    }

    public List<Course> findCourseByUserId(User user){
        return courseRepository.findByUser(user);
    }

    public void editCourse(Course original, Course edited){
        original.setCategory(edited.getCategory());
        original.setContent(edited.getContent());
        original.setIntroduction(edited.getIntroduction());
        original.setPeopleSuite(edited.getPeopleSuite());
        original.setPrice(edited.getPrice());
        original.setTitle(edited.getTitle());
        insertCourse(original);
    }

    public List<Course> teacherSearchCourse(SearchCourseRequest searchCourseRequest, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.NAME){
            return courseRepository.findByUserAndTitleIgoneCase(user.getId(), "%"+searchCourseRequest.getSearchWords().trim().toUpperCase()+"%");
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.CATEGORY) {
            return courseRepository.findByUserAndCategory(user, searchCourseRequest.getCategory());
        }
        return null;
    }
}
