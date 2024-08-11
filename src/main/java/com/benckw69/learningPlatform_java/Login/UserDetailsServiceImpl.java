package com.benckw69.learningPlatform_java.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.benckw69.learningPlatform_java.User.LoginMethod;
import com.benckw69.learningPlatform_java.User.UserRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    private HttpSession httpSession;

    public UserDetailsServiceImpl (HttpSession httpSession){
        this.httpSession = httpSession;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.benckw69.learningPlatform_java.User.User user = userRepository.findByEmailIgnoreCaseAndLoginMethodAndIsDeleted(username,LoginMethod.email,false);
        if (user == null) {
            throw new UsernameNotFoundException("Can't find member: " + username);
        }
        
        httpSession.setAttribute("user", user);
        httpSession.setAttribute("userId", user.getId());

        return User
                .withUsername(username)
                .password(user.getPassword())
                .authorities(user.getType().name())
                .build();
    }
}