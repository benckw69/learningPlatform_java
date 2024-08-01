package com.benckw69.learningPlatform_java.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class CustomLogoutHandler implements LogoutHandler {
    private final HttpSession httpSession;
    @Autowired
    public CustomLogoutHandler(HttpSession httpSession){
        this.httpSession = httpSession;
    }
    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res, Authentication aut){
        httpSession.removeAttribute("user");
    }
}
