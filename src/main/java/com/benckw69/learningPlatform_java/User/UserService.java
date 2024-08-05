package com.benckw69.learningPlatform_java.User;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    IntroductionRepository introductionRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Boolean isSamePassword(String password, String password_repeat, Model model){
        if(!password.equals(password_repeat)) {
            model.addAttribute("password_repeat_error", "確認密碼與密碼不一樣");
            return false;
        }
        return true;
    }

    public Boolean emailExist(String email,Model model){
        User existEmail = userRepository.findByEmailAndLoginMethod(email,LoginMethod.email);
        if(existEmail != null) {
            model.addAttribute("email_error", "此電郵地址已被註冊");
            return true;
        }
        return false;
    }

    public Boolean register(RegisterRequest registerRequest,Model model){
        //Validation: confirm password and email existance
        Boolean validation = !emailExist(registerRequest.getEmail(), model);
        validation = isSamePassword(registerRequest.getPassword(), registerRequest.getPassword_repeat(), model) && validation? true:false;
        if(!validation) return false;

        //Set new user
        User newUser = new User();
        newUser.setType(registerRequest.getType());
        newUser.setEmail(registerRequest.getEmail());
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setLoginMethod(LoginMethod.email);

        //referral system. Default both users get the price
        if(registerRequest.getReferral()!=null && registerRequest.getReferral()!=""){
            Integer referralId = Integer.parseInt(registerRequest.getReferral());
            User referral = userRepository.findByIdAndIsDeleted(referralId,false).orElse(null);
            Integer balance = 10;
            if(referral != null) {
                userRepository.updateBalance(balance, referralId);
                newUser.setBalance(balance);
            }
        }

        //save new user
        newUser = userRepository.save(newUser);
        if(registerRequest.getType() == Type.teacher){
            Introduction newIntroduction = new Introduction();
            newIntroduction.setUser(newUser);
            newIntroduction.setIntrodution("Hello I am "+registerRequest.getUsername()+".");
            introductionRepository.save(newIntroduction);
        }
        return true;
    }

    public Introduction getIntroductionBySession(HttpSession httpSession){
        return introductionRepository.findById((Integer)httpSession.getAttribute("userId")).orElse(null);
    }

    public User getUserBySession(HttpSession httpSession){
        return (User)httpSession.getAttribute("user");
    }

    public void updateInfoTeacher(TeacherEdit teacherEdit, HttpSession httpSession){
        Introduction introduction = getIntroductionBySession(httpSession);
        User user = getUserBySession(httpSession);
        user.setEmail(teacherEdit.getEmail());
        user.setUsername(teacherEdit.getUsername());
        introduction.setIntrodution(teacherEdit.getIntroduction());
        userRepository.save(user);
        introductionRepository.save(introduction);
    }

    public Boolean validInfoEdit(StudentOrAdminEdit studentOrAdminEdit, Model model, HttpSession httpSession){
        //get the entity for edit use
        String password = studentOrAdminEdit.getPassword();
        User user = getUserBySession(httpSession);
        //check valid password and whether the email address has been used
        if(!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("password_error", "密碼錯誤");
            return false;
        }
        if(!studentOrAdminEdit.getEmail().equals(user.getEmail()) && emailExist(studentOrAdminEdit.getEmail(), model)) return false;
        return true;
    }

    public void updateInfoStudentOrAdmin(StudentOrAdminEdit studentOrAdminEdit, HttpSession httpSession){
        User user = getUserBySession(httpSession);
        user.setEmail(studentOrAdminEdit.getEmail());
        user.setUsername(studentOrAdminEdit.getUsername());
        userRepository.save(user);
    }
}
