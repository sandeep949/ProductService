package com.project.ProductService.Config;

import com.project.ProductService.Interceptors.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig  {


//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**") // Apply CORS to all endpoints
//                        .allowedOrigins("http://localhost:4200") // Allow Angular frontend
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow these HTTP methods
//                        .allowedHeaders("*") // Allow all headers
//                        .allowCredentials(true); // Allow cookies/authentication headers
//            }
//        };
//
//    }
}


