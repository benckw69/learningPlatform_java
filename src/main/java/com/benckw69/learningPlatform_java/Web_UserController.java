package com.benckw69.learningPlatform_java;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class Web_UserController {

    @GetMapping("/")
    public String showUserInfomation(){
        return "user_view";
    }

    @GetMapping("/edit/info")
    public String editUserInfomation(){
        return "user_edit_info";
    }

    @GetMapping("/edit/pass")
    public String editPassword(){
        return "user_edit_pass";
    }

}
