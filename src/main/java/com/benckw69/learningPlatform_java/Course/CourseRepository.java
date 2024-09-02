package com.benckw69.learningPlatform_java.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.benckw69.learningPlatform_java.User.User;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    //check whether it has the title
    Course findByTitle(String title);

    //Student: find all, find by title, category or teacher name
    List<Course> findAllByOrderByIdDesc();
    List<Course> findByTitleContainsIgnoreCaseOrderByIdDesc(String title);
    List<Course> findByCategoryOrderByIdDesc(Category category);
    List<Course> findByUserInOrderByIdDesc(List<User> users);

    //Student: find paid course, find by title, category or teacher name
    List<Course> findByIdInOrderByIdDesc(List<Integer> ids);
    List<Course> findByTitleContainsIgnoreCaseAndIdInOrderByIdDesc(String title, List<Integer> ids);
    List<Course> findByCategoryAndIdInOrderByIdDesc(Category category, List<Integer> ids);
    List<Course> findByUserInAndIdInOrderByIdDesc(List<User> users, List<Integer> ids);

    //find by user id
    List<Course> findByUserAndIsDeletedOrderByIdDesc(User user, Boolean isDeleted);

    //Teacher: find by category or title
    List<Course> findByUserAndCategoryAndIsDeletedOrderByIdDesc(User user, Category category, Boolean isDeleted);
    List<Course> findByUserAndTitleContainsIgnoreCaseAndIsDeletedOrderByIdDesc(User user, String title, Boolean isDeleted);

    Course findByIdAndIsDeleted(Integer id, Boolean isDeleted);
}
