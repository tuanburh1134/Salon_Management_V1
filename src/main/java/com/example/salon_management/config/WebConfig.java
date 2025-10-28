package com.example.salon_management.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * ✅ Cho phép truy cập ảnh trong thư mục uploads/
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Tạo đường dẫn tuyệt đối đến thư mục "uploads"
        String uploadPath = Paths.get(System.getProperty("user.dir"), "uploads")
                .toUri()
                .toString();

        // Cho phép truy cập ảnh qua URL /uploads/**
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }

    /**
     * ✅ Thêm converter tự động chuyển đổi giữa String và LocalDateTime
     * dùng cho form input type="datetime-local"
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // String → LocalDateTime
        registry.addConverter(String.class, LocalDateTime.class, source -> {
            if (source == null || source.trim().isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        });

        // LocalDateTime → String
        registry.addConverter(LocalDateTime.class, String.class, source -> {
            if (source == null) {
                return null;
            }
            return source.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        });
    }
}
