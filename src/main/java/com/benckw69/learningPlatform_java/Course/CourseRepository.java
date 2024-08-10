package com.benckw69.learningPlatform_java.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.benckw69.learningPlatform_java.User.User;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    //check whether it has the title
    Course findByTitle(String title);

    //find by user id
    List<Course> findByUser(User user);

    //search by category or title from teacher
    List<Course> findByUserAndCategory(User user, Category category);
    @Query(value = "Select * FROM course where teacher_id=?1 AND UPPER(title) LIKE ?2", nativeQuery = true)
    List<Course> findByUserAndTitleIgoneCase(Integer id, String title);

}
