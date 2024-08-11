package com.benckw69.learningPlatform_java.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.benckw69.learningPlatform_java.Search.SearchCourseMethod;
import com.benckw69.learningPlatform_java.Search.SearchCourseRequest;
import com.benckw69.learningPlatform_java.User.User;
import com.benckw69.learningPlatform_java.User.UserService;
import com.benckw69.learningPlatform_java.storage.FileType;

import jakarta.servlet.http.HttpSession;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    BuyRecordService buyRecordService;

    @Autowired
    UserService userservice;

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

    public List<Course> findOwnCourseByTeacherId(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        return courseRepository.findByUserOrderByIdDesc(user);
    }

    public List<Course> findOwnCourseByStudentId(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        List<BuyRecord> buyRecords = buyRecordService.findBuyRecordByUserId(user);
        List<Course> courses = new ArrayList<>();
        for(BuyRecord buyRecord : buyRecords){
            courses.add(buyRecord.getCourse());
        }
        return courses;
    }

    public List<Course> findAllCourse(){
        return courseRepository.findAllByOrderByIdDesc();
    }

    public List<Course> findAllCourseBySearch(SearchCourseRequest searchCourseRequest){
        if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.NAME){
            return courseRepository.findByTitleContainsIgnoreCaseOrderByIdDesc(searchCourseRequest.getSearchWords());
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.CATEGORY) {
            return courseRepository.findByCategoryOrderByIdDesc(searchCourseRequest.getCategory());
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.TEACHER) {
            List<User> users = userservice.getUsersByUsernameOrderByDate(searchCourseRequest.getSearchWords());
            return courseRepository.findByUserInOrderByIdDesc(users);
        }
        return null;
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

    public List<Course> teacherSearchOwnCourse(SearchCourseRequest searchCourseRequest, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.NAME){
            return courseRepository.findByUserAndTitleContainsIgnoreCaseOrderByIdDesc(user, searchCourseRequest.getSearchWords().trim());
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.CATEGORY) {
            return courseRepository.findByUserAndCategoryOrderByIdDesc(user, searchCourseRequest.getCategory());
        }
        return null;
    }

    public List<Course> studentSearchOwnCourse(SearchCourseRequest searchCourseRequest, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        List<BuyRecord> buyRecords = buyRecordService.findBuyRecordByUserId(user);
        List<Course> courseBought = buyRecords.stream().map(BuyRecord::getCourse).collect(Collectors.toList());
        List<Integer> courseId = courseBought.stream().map(Course::getId).collect(Collectors.toList());
        if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.NAME){
            return courseRepository.findByTitleContainsIgnoreCaseAndIdInOrderByIdDesc(searchCourseRequest.getSearchWords(),courseId);
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.CATEGORY) {
            return courseRepository.findByCategoryAndIdInOrderByIdDesc(searchCourseRequest.getCategory(), courseId);
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.TEACHER) {
            List<User> users = userservice.getUsersByUsernameOrderByDate(searchCourseRequest.getSearchWords());
            return courseRepository.findByUserInAndIdInOrderByIdDesc(users, courseId);
        }
        return null;
    }

    public void update(Course course, PhotoType extension){
        course.setPhotoType(extension);
        courseRepository.save(course);
    }

    public void update(Course course, VideoType extension){
        course.setVideoType(extension);
        courseRepository.save(course);
    }

    public List<Course> findCourseByStudentId(User user){
        
        return null;
    }
}
