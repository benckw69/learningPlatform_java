package com.benckw69.learningPlatform_java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class Web_IndexController {

    
    @Autowired
    private UserService userService;

    @GetMapping
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
    public String form(FormRegister formRegister){
        return "pages/register";
    }

    @PostMapping("/register")
    public String RegisterFormHandle(@Valid FormRegister formRegister,BindingResult bindingResult, Model model){
        Boolean isRegister = userService.register(formRegister,model);
        if(bindingResult.hasErrors() || !isRegister) return "pages/register";
        return "pages/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/login";
    }

    //handle login
    @PostMapping("/login")
    public String loginAuthentication(){
        

        if(true) return "redirect:/";
        else return "redirect:/login";
    }


    
}
