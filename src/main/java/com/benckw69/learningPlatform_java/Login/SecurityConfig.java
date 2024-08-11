package com.benckw69.learningPlatform_java.Login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

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
                        .defaultSuccessUrl("/")
                        )
                        .logout((logout) -> logout.logoutUrl("/logout")
                                .logoutSuccessUrl("/login"))
                        .authorizeHttpRequests(requests -> requests
                                //.shouldFilterAllDispatcherTypes(false)
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/teacher/moneySeperation").hasAuthority("teacher")
                                .requestMatchers("/student/course/search","/student/course/own").hasAuthority("student")
                                .requestMatchers("/teacher/course/own").hasAuthority("teacher")
                                .requestMatchers("/register","login").permitAll() //allowed for both get and post request
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers("/css/**", "/images/**","/members").permitAll()
                                .requestMatchers("/teacher/studentSearch").hasAuthority("teacher")
                                .requestMatchers("/student/teacherSearch").hasAuthority("student")
                                .requestMatchers("/user","/user/info/edit","user/pw/edit").hasAnyAuthority("teacher", "admin","student")
                                .requestMatchers("/user/info/edit/teacher").hasAuthority("teacher")
                                .requestMatchers("/user/info/edit/studentOrAdmin").hasAnyAuthority("admin", "student")
                                .requestMatchers(HttpMethod.POST, "/user/delete").hasAnyAuthority("teacher", "student")
                                .requestMatchers("/admin/referral","/admin/userDeleteRecords","/admin/userDeleteRecords/*").hasAuthority("admin")
                                .requestMatchers("/admin/moneyTickets/view","/admin/moneyTickets/delete/*","/admin/moneyTickets/insert","/admin/moneySeperation").hasAuthority("admin")
                                .requestMatchers("/moneyRecords").hasAnyAuthority("teacher", "admin","student")
                                .requestMatchers("/media/*").hasAnyAuthority("student","teacher")
                                .anyRequest().authenticated()
                        )
                        
                        .csrf().disable()
                        .build();
        }

    

}