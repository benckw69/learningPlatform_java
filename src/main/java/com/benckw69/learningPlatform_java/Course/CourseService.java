package com.benckw69.learningPlatform_java.Course;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.benckw69.learningPlatform_java.AdminConfig.EventCategory;
import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecord;
import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecordService;
import com.benckw69.learningPlatform_java.AdminConfig.MoneySeperation;
import com.benckw69.learningPlatform_java.AdminConfig.MoneySeperationService;
import com.benckw69.learningPlatform_java.Search.SearchCourseMethod;
import com.benckw69.learningPlatform_java.Search.SearchCourseRequest;
import com.benckw69.learningPlatform_java.User.User;
import com.benckw69.learningPlatform_java.User.UserService;

import jakarta.servlet.http.HttpSession;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    BuyRecordService buyRecordService;

    @Autowired
    UserService userservice;

    @Autowired
    MoneyRecordService moneyRecordService;

    @Autowired
    MoneySeperationService moneySeperationService;

    @Autowired
    RatingService ratingService;

    public Course insertCourse(Course course){
        return courseRepository.save(course);
    }

    public Boolean sameTitle(String title, Model model){
        Course course = courseRepository.findByTitle(title);
        if(course!=null) model.addAttribute("titleError", "標題已被使用");
        return course == null? false : true;
    }

    public Course findCourseById(Integer Id){
        return courseRepository.findByIdAndIsDeleted(Id,false);
    }

    public List<Course> findOwnCourseByTeacherId(HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        return courseRepository.findByUserAndIsDeletedOrderByIdDesc(user,false);
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
        List<Course> courses =  courseRepository.findAllByOrderByIdDesc();
        List<Course> coursesWithNotDeletedUser = courses.stream().filter(course->course.getUser().getIsDeleted()==false).collect(Collectors.toList());
        return coursesWithNotDeletedUser;
    }

    public List<Course> findAllCourseBySearch(SearchCourseRequest searchCourseRequest){
        List<Course> courses = new ArrayList<>();
        if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.NAME){
            courses = courseRepository.findByTitleContainsIgnoreCaseOrderByIdDesc(searchCourseRequest.getSearchWords());
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.CATEGORY) {
            courses = courseRepository.findByCategoryOrderByIdDesc(searchCourseRequest.getCategory());
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.TEACHER) {
            List<User> users = userservice.getUsersByUsernameOrderByDate(searchCourseRequest.getSearchWords());
            courses = courseRepository.findByUserInOrderByIdDesc(users);
        }
        List<Course> coursesWithNotDeletedUser = courses.stream().filter(course->course.getUser().getIsDeleted()==false).collect(Collectors.toList());
        return coursesWithNotDeletedUser;
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
            return courseRepository.findByUserAndTitleContainsIgnoreCaseAndIsDeletedOrderByIdDesc(user, searchCourseRequest.getSearchWords().trim(),false);
        } else if(searchCourseRequest.getSearchCourseMethod() == SearchCourseMethod.CATEGORY) {
            return courseRepository.findByUserAndCategoryAndIsDeletedOrderByIdDesc(user, searchCourseRequest.getCategory(),false);
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

    public Course getCourse(Integer id){
        return courseRepository.findById(id).orElse(null);
    }

    public Boolean paid(User user, Course course){
        return buyRecordService.getBuyRecordByUserIdAndCourseId(course, user) == null? false : true;
    }

    public Boolean validShowCourseToStudent(Course course,HttpSession httpSession){
        //first, check whether it is null
        if(course==null) return false;

        //second, check whether the student bought the course
        User user = (User)httpSession.getAttribute("user");
        if(paid(user,course)) return true;

        //third, check whether the course is not deleted yet and user is not deleted yet
        if(course.getIsDeleted() == false && course.getUser().getIsDeleted() == false) return true;
        return false;
    }

    public Boolean buyCourse(Course course, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        if(user.getBalance() >= course.getPrice()){
            //buy the course. Insert buy record and insert money records.  Update balance of student, teacher and admin.

            //1. insert buy record
            BuyRecord buyRecord = new BuyRecord();
            buyRecord.setCourse(course);
            buyRecord.setUser(user);
            buyRecordService.updateBuyRecord(buyRecord);

            //2. update student's balance and insert student's money record
            user.setBalance(user.getBalance()-course.getPrice());
            userservice.updateUser(user);
            MoneyRecord studentMoneyRecord = new MoneyRecord();
            studentMoneyRecord.setMoneyChange(-course.getPrice());
            studentMoneyRecord.setEventCategory(EventCategory.BUY_COURSE);
            studentMoneyRecord.setEventConsequence(1);
            studentMoneyRecord.setUser(user);
            studentMoneyRecord.setEventText(EventCategory.BUY_COURSE, user, course);
            moneyRecordService.updateMoneyRecord(studentMoneyRecord);

            //3. update teacher's balance and insert teacher's money record

            //load money seperation setting
            MoneySeperation moneySeperation = moneySeperationService.getMoneySeperation();
            Integer teacherProfit = course.getPrice() * moneySeperation.getTeacherMoneyPercentage() / 100;
            Integer adminProfit = course.getPrice() - teacherProfit;

            User teacher = course.getUser();
            teacher.setBalance(teacher.getBalance() + teacherProfit);
            userservice.updateUser(teacher);
            MoneyRecord teacherMoneyRecord = new MoneyRecord();
            teacherMoneyRecord.setMoneyChange(teacherProfit);
            teacherMoneyRecord.setEventCategory(EventCategory.BUY_COURSE);
            teacherMoneyRecord.setEventConsequence(2);
            teacherMoneyRecord.setUser(teacher);
            teacherMoneyRecord.setEventText(EventCategory.BUY_COURSE, user, teacher, course, moneySeperation);
            moneyRecordService.updateMoneyRecord(teacherMoneyRecord);

            //4. update admin's balance and insert admin's money record
            User admin = userservice.getAdmin();
            admin.setBalance(admin.getBalance() + adminProfit);
            userservice.updateUser(admin);
            MoneyRecord adminMoneyRecord = new MoneyRecord();
            adminMoneyRecord.setMoneyChange(adminProfit);
            adminMoneyRecord.setEventCategory(EventCategory.BUY_COURSE);
            adminMoneyRecord.setEventConsequence(3);
            adminMoneyRecord.setUser(admin);
            adminMoneyRecord.setEventText(EventCategory.BUY_COURSE, user, admin, course, moneySeperation);
            moneyRecordService.updateMoneyRecord(adminMoneyRecord);
            return true;
        } else return false;
    }

    public List<CourseWithDetails> calcDetails(List<Course> courses){
        List<CourseWithDetails> coursesWithDetails = new ArrayList<>();
        for(Course course : courses){
            //calculate how many people bought the course and the average rating of people
            CourseWithDetails courseWithDetails = new CourseWithDetails(course);

            courseWithDetails.setNoOfStudents(courseWithDetails.getBuyRecord().size());

            Set<BuyRecord> buyRecords = courseWithDetails.getBuyRecord();
            List<Rating> ratings = ratingService.getRatingsByBuyRecords(buyRecords);
            courseWithDetails.setNoOfRates(ratings.size());
            Double rating = ratings.stream().map(Rating::getRate).mapToDouble(a->a).average().orElse(-1);
            if(rating != -1 ) rating = ((double)Math.round(rating*10)) / 10;
            courseWithDetails.setRate(rating);
            coursesWithDetails.add(courseWithDetails);
        }
        return coursesWithDetails;
    }
}
