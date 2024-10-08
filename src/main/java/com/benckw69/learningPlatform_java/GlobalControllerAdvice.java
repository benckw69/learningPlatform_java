package com.benckw69.learningPlatform_java;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.benckw69.learningPlatform_java.User.User;
import com.benckw69.learningPlatform_java.User.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalControllerAdvice {
    @Autowired
    UserRepository userRepository;

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @ModelAttribute("title")
    public String title(HttpSession httpSession, Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        //
        if(httpSession.getAttribute("userId") != null){
            User user = userRepository.findByIdAndIsDeleted((Integer)httpSession.getAttribute("userId"),false).orElse(null);
            if(user != null){
                httpSession.setAttribute("user", user);
            } else {
                this.logoutHandler.logout(request,response,authentication);
            }
        }


        Global global = new Global();
        return global.getTitle();
    }
}