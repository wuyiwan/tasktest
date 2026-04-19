package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imageDir = "D:/image/";
        File dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdirs();
            System.out.println("Created image directory: " + imageDir);
        }
        
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imageDir);
        
        System.out.println("Static resource mapping: /images/** -> file:" + imageDir);
    }
}
