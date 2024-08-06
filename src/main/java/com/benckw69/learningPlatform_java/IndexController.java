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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.benckw69.learningPlatform_java.AdminConfig.MoneyRecordService;
import com.benckw69.learningPlatform_java.User.RegisterRequest;
import com.benckw69.learningPlatform_java.User.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class IndexController {

    
    @Autowired
    private UserService userService;

    @Autowired
    private MoneyRecordService moneyRecordService;

    @GetMapping({"/","/index"})
    public String index(HttpSession session){
        return "pages/index";
    }

    @GetMapping("/login")
    public String login(){
        return "pages/login";
    }

    @GetMapping("/register")
    public String form(RegisterRequest registerRequest,@RequestParam(required = false) String ref){
        registerRequest.setReferral(ref);
        return "pages/register";
    }

    @PostMapping("/register")
    public String RegisterFormHandle(@Valid RegisterRequest registerRequest,BindingResult bindingResult, Model model){
        Boolean validation = userService.validRegister(registerRequest,model);
        if(bindingResult.hasErrors() || !validation) return "pages/register";
        userService.register(registerRequest);
        return "redirect:/login?register=true";
    }

    @GetMapping("/moneyRecords")
    public String moneyRecords(HttpSession httpSession, Model model){
        model.addAttribute("moneyRecords", moneyRecordService.getMoneyRecordByUserId(httpSession));
        return "pages/money_records";
    }

    
}
