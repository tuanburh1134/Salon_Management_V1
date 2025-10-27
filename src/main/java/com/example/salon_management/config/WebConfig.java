package com.example.salon_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // Converter for LocalDateTime to String (for datetime-local input)
        registry.addConverter(String.class, LocalDateTime.class, source -> {
            if (source == null || source.trim().isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        });

        // Converter for LocalDateTime to String (for display)
        registry.addConverter(LocalDateTime.class, String.class, source -> {
            if (source == null) {
                return null;
            }
            return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        });
    }
}
