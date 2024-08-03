package com.benckw69.learningPlatform_java.Login;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class SecurityAuthSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 验证成功后执行
     * @param request 请求对象
     * @param response 响应对象
     * @param authentication security验证成功后的封装对象，包括用户的信息
     * @throws IOException
     * @throws ServletException
     */

    private HttpSession httpSession;

    public SecurityAuthSuccessHandler(HttpSession httpSession){
        this.httpSession = httpSession;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication aut) throws IOException, ServletException {
        System.out.println("successfully login");
        httpSession.setAttribute("user", "student");
        res.sendRedirect("/");
    }
}
  
