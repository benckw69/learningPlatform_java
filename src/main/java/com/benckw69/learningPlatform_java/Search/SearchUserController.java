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
@RequestMapping("/")
public class SearchUserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/teacher/studentSearch")
    public String searchStudents(SearchUserRequest searchUserRequest){
        return "pages/teacher_searchStudent";
    }

    @PostMapping("/teacher/studentSearch")
    public String searchStudents(SearchUserRequest searchUserRequest, Model model){
        if(searchUserRequest.getSearchUserMethod()==SearchUserMethod.EMAIL){
            model.addAttribute("result", userRepository.findByEmailContainsIgnoreCaseAndType(searchUserRequest.getSearchWords().trim(),Type.student));
        } else {
            model.addAttribute("result", userRepository.findByUsernameContainsIgnoreCaseAndType(searchUserRequest.getSearchWords().trim(),Type.student));
        }
        return "pages/teacher_searchStudent";
    }

    @GetMapping("/student/teacherSearch")
    public String searchTeachers(SearchUserRequest searchUserRequest){
        return "pages/student_searchTeacher";
    }

    @PostMapping("/student/teacherSearch")
    public String searchTeachers(SearchUserRequest searchUserRequest, Model model){
        if(searchUserRequest.getSearchUserMethod()==SearchUserMethod.EMAIL){
            model.addAttribute("result", userRepository.findByEmailContainsIgnoreCaseAndType(searchUserRequest.getSearchWords().trim(),Type.teacher));
        } else {
            model.addAttribute("result", userRepository.findByUsernameContainsIgnoreCaseAndType(searchUserRequest.getSearchWords().trim(),Type.teacher));
        }
        return "pages/student_searchTeacher";
    }
}
