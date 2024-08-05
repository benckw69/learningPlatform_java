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
                                .requestMatchers("/register").permitAll() //allowed for both get and post request
                                .requestMatchers(HttpMethod.GET, "/").permitAll()
                                .requestMatchers("/login","/error").permitAll()
                                .requestMatchers("/css/**", "/images/**","/members").permitAll()
                                .requestMatchers(HttpMethod.GET, "/selected-courses").hasAuthority("student")
                                .requestMatchers(HttpMethod.GET, "/course-feedback").hasAnyAuthority("TEACHER", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/members").hasAuthority("ADMIN")
                                .requestMatchers("/search/teachers").hasAuthority("student")
                                .requestMatchers("/search/students").hasAuthority("teacher")
                                .requestMatchers("/user","/user/info/edit","user/pw/edit").hasAnyAuthority("teacher", "admin","student")
                                .anyRequest().authenticated()
                        )
                        
                        .csrf().disable()
                        .build();
        }

    

}