package com.benckw69.learningPlatform_java;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @ModelAttribute("title")
    public String messages() {
        Global global = new Global();
        return global.getTitle();
    }

    
    @GetMapping("/")
    public String index(HttpSession session){
        session.setAttribute("user", "teacher");
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(){
        return "pages/login";
    }

    @GetMapping("/register")
    public String register(){
        return "pages/register";
    }
}
