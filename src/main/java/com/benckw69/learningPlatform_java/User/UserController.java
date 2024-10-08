package com.benckw69.learningPlatform_java.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    IntroductionService introductionService;
    
    @GetMapping
    public String showUserInformation(Model model,HttpSession httpSession){
        //retrieve introduction if user type is teacher
        if((User)httpSession.getAttribute("user") != null && ((User)httpSession.getAttribute("user")).getType()==Type.teacher){
            Introduction introduction = introductionService.getIntroductionBySession(httpSession);
            model.addAttribute("introduction", introduction.getIntroduction());
        }
        return "pages/user_info_view";
    }

    @GetMapping({"/info/edit","/info/edit/teacher","/info/edit/studentOrAdmin"})
    public String editUserInformation(StudentOrAdminEdit studentOrAdminEdit, TeacherEdit teacherEdit, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        Type type = user.getType();

        if((User)httpSession.getAttribute("user") != null && (type==Type.teacher)){
            Introduction introduction = introductionService.getIntroductionBySession(httpSession);
            teacherEdit.setUsername(user.getUsername());
            teacherEdit.setEmail(user.getEmail());
            teacherEdit.setIntroduction(introduction.getIntroduction());
            return "pages/user_info_edit_teacher";
        } else {
            studentOrAdminEdit.setUsername(user.getUsername());
            studentOrAdminEdit.setEmail(user.getEmail());
            return "pages/user_info_edit_studentOrAdmin";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(HttpSession httpSession){
        //set user's delete flag to true
        userService.deleteUser(httpSession);
        return "redirect:/logout";
    }

    @PostMapping("/info/edit/teacher")
    public String editUserInfoTeacher(@Valid TeacherEdit teacherEdit, BindingResult bindingResult, Model model, HttpSession httpSession){
        Boolean validation = userService.validInfoEdit(teacherEdit,model,httpSession);
        if(!validation || bindingResult.hasErrors()) return "pages/user_info_edit_teacher";
        userService.updateInfoTeacher(teacherEdit,httpSession);
        return "redirect:/user/info/edit?edit=true";
    }

    @PostMapping("/info/edit/studentOrAdmin")
    public String editUserInfoStudentOrAdmin(@Valid StudentOrAdminEdit studentOrAdminEdit, BindingResult bindingResult, Model model, HttpSession httpSession){
        Boolean validation = userService.validInfoEdit(studentOrAdminEdit,model,httpSession);
        if(!validation || bindingResult.hasErrors()) return "pages/user_info_edit_studentOrAdmin";
        userService.updateInfoStudentOrAdmin(studentOrAdminEdit,httpSession);
        return "redirect:/user/info/edit?edit=true";
    }

    @GetMapping("/pw/edit")
    public String editPassword(PasswordEdit passwordEdit){
        return "pages/user_pw_edit";
    }

    @PostMapping("/pw/edit")
    public String editPassword(PasswordEdit passwordEdit, BindingResult bindingResult, Model model, HttpSession httpSession){
        User user = (User)httpSession.getAttribute("user");
        Boolean validation = userService.validNewPassword(passwordEdit,user.getPassword(),model);
        if(!validation || bindingResult.hasErrors()) return "pages/user_pw_edit";
        userService.updatePassword(passwordEdit,httpSession);
        return "redirect:/user/pw/edit?edit=true";
    }

}
