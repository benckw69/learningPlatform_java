package com.benckw69.learningPlatform_java.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.benckw69.learningPlatform_java.Course.Course;

import jakarta.servlet.http.HttpSession;

@Service
public class IntroductionService {
    @Autowired
    IntroductionRepository introductionRepository;

    public Introduction getIntroductionByCourseProducer(Course course){
        return introductionRepository.findById(course.getUser().getId()).orElse(null);
    }

    public Introduction getIntroductionBySession(HttpSession httpSession){
        return introductionRepository.findById((Integer)httpSession.getAttribute("userId")).orElse(null);
    }

    public Introduction updateIntroduction(Introduction introduction){
        return introductionRepository.save(introduction);
    }
}
