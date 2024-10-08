package com.benckw69.learningPlatform_java;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdditionalResourceWebConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/media/**").addResourceLocations("file:media/");
    registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
  }
}