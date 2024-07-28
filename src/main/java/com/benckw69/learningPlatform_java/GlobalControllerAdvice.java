package com.benckw69.learningPlatform_java;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {

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
}