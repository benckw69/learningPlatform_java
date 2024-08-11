package com.benckw69.learningPlatform_java.Course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.benckw69.learningPlatform_java.User.User;

@Repository
public interface BuyRecordRepository extends JpaRepository<BuyRecord, Integer> {
    List<BuyRecord> findByUserOrderByCreatedTimeDesc(User user);

    BuyRecord findByUserAndCourse(User user, Course course);
}
