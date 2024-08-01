package com.benckw69.learningPlatform_java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/register1")
    public String register() {
        return "註冊畫面";
    }
    
    @GetMapping("/home")
    public String home() {
        return "系統首頁";
    }

    @GetMapping("/selected-courses")
    public String selectedCourses() {
        return "修課清單";
    }

    @GetMapping("/course-feedback")
    public String courseFeedback() {
        return "課程回饋";
    }

    @GetMapping("/members")
    public Boolean members() {
        return passwordEncoder.matches("11111111", "$2a$10$b/mT6NyuDK6yhD4hIIGy1empTV2Ddotz8HatWm.pZAwXDKTrQJ8YG");
    }
}