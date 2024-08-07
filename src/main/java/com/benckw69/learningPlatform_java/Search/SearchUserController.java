package com.benckw69.learningPlatform_java.Search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.benckw69.learningPlatform_java.User.Type;
import com.benckw69.learningPlatform_java.User.UserRepository;

@Controller
@RequestMapping("/search")
public class SearchUserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/students")
    public String searchStudents(SearchUserRequest searchUserRequest){
        return "pages/student_search";
    }

    @PostMapping("/students")
    public String searchStudents(SearchUserRequest searchUserRequest, Model model){
        if(searchUserRequest.getSearchUserMethod()==SearchUserMethod.email){
            model.addAttribute("result", userRepository.findByEmailContainsAndType(searchUserRequest.getSearchWords(),Type.student));
        } else {
            model.addAttribute("result", userRepository.findByUsernameContainsAndType(searchUserRequest.getSearchWords(),Type.student));
        }
        return "pages/student_search";
    }

    @GetMapping("/teachers")
    public String searchTeachers(SearchUserRequest searchUserRequest){
        return "pages/teacher_search";
    }

    @PostMapping("/teachers")
    public String searchTeachers(SearchUserRequest searchUserRequest, Model model){
        if(searchUserRequest.getSearchUserMethod()==SearchUserMethod.email){
            model.addAttribute("result", userRepository.findByEmailContainsAndType(searchUserRequest.getSearchWords(),Type.teacher));
        } else {
            model.addAttribute("result", userRepository.findByUsernameContainsAndType(searchUserRequest.getSearchWords(),Type.teacher));
        }
        return "pages/teacher_search";
    }
}