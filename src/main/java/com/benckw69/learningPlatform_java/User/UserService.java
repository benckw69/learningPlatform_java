package com.benckw69.learningPlatform_java.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User saveUser(User user){
        User newUser = userRepository.save(user);
        return newUser;
    }
    //check whether email exist
    public void addNewUser(User user){

        userRepository.findByEmail(user.getEmail());
    }

    public Boolean register(RegisterRequest formRegister,Model model){
        Boolean isRegister = true;
        //Validation: confirm password and email existance
        if(!formRegister.getPassword().equals(formRegister.getPassword_repeat())) {
            model.addAttribute("password_repeat_error", "確認密碼與密碼不一樣");
            isRegister = false;
        }
        User existUser = userRepository.findUserExist(formRegister.getEmail());
        if(existUser != null) {
            model.addAttribute("email_error", "此電郵地址已被註冊");
            isRegister = false;
        }
        if(!isRegister) return isRegister;

        //Set new user
        User newUser = new User();
        newUser.setType(formRegister.getType());
        newUser.setEmail(formRegister.getEmail());
        newUser.setUsername(formRegister.getUsername());
        newUser.setPassword(passwordEncoder.encode(formRegister.getPassword()));
        newUser.setLoginMethod(LoginMethod.email);

        //check email
        newUser = userRepository.save(newUser);
        System.out.println(newUser.getId());
        return true;
    }
}
