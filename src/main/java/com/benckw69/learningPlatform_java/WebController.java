package com.benckw69.learningPlatform_java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {

    @ModelAttribute("title")
    public String title() {
        Global global = new Global();
        return global.getTitle();
    }

    @ModelAttribute("messages")
    public List<String> messages(HttpSession session) {
        List<String> messages = null;
        if(session.getAttribute("messages") != null){
            messages = (List<String>)session.getAttribute("messages");
            session.removeAttribute("messages");
        }
        return messages;
    }

    
    @GetMapping("/")
    public String index(HttpSession session){
        //this sentence need to be remove.  Just illustration
        session.setAttribute("user", "teacher");
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model){
        
        List<String> arraylist = new ArrayList<>();
        arraylist.add("123");
        arraylist.add("456");
        session.setAttribute("messages", arraylist);

        return "pages/login";
    }

    @GetMapping("/register")
    public String register(HttpSession session, Model model){
        return "pages/register";
        
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/login";
    }
}
