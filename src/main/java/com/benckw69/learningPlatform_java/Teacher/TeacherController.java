package com.benckw69.learningPlatform_java.Teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.benckw69.learningPlatform_java.AdminConfig.MoneySeperationService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    MoneySeperationService moneySeperationService; 

    @GetMapping("/moneySeperation")
    public String moneySeperationEdit(Model model){
        model.addAttribute("moneySeperation", moneySeperationService.getMoneySeperation());
        return "pages/teacher_money_seperation";
    }
}
