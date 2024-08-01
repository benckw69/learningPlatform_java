package com.benckw69.learningPlatform_java.Login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig{


        @Autowired
        LogoutHandler CustomLogoutHandler;

        @Autowired
        SecurityAuthSuccessHandler securityAuthSuccessHandler;

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
                return httpSecurity
                        .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error=login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/login")
                        .successHandler(securityAuthSuccessHandler)
                        //.defaultSuccessUrl("/")
                        )
                        .logout((logout) -> logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
                                .addLogoutHandler(CustomLogoutHandler))
                        .authorizeHttpRequests(requests -> requests
                                .requestMatchers(HttpMethod.GET, "/register").permitAll()
                                .requestMatchers(HttpMethod.POST, "/register").permitAll()
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/login").permitAll()
                                .requestMatchers("/css/**", "/images/**","/members").permitAll()
                                .requestMatchers(HttpMethod.GET, "/selected-courses").hasAuthority("student")
                                .requestMatchers(HttpMethod.GET, "/course-feedback").hasAnyAuthority("TEACHER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/members").hasAuthority("ADMIN")
                                .anyRequest().authenticated()
                        )
                        .csrf().disable()
                        .build();
        }

    

}