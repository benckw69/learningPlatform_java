package com.benckw69.learningPlatform_java;

import java.util.Arrays;

import com.benckw69.learningPlatform_java.AdminConfig.MoneySeperationService;
import com.benckw69.learningPlatform_java.AdminConfig.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.benckw69.learningPlatform_java.User.UserService;
import com.benckw69.learningPlatform_java.storage.StorageProperties;
import com.benckw69.learningPlatform_java.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class LearningPlatformJavaApplication {

	@Autowired
	UserService userService;

	@Autowired
	MoneySeperationService moneySeperationService;

	@Autowired
	ReferralService referralService;

	public static void main(String[] args) {
		SpringApplication.run(LearningPlatformJavaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
			userService.init();
			moneySeperationService.init();
			referralService.init();

		};
	}
}
