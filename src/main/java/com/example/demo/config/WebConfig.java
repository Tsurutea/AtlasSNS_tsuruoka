// src/main/java/com/example/demo/config/WebConfig.java
package com.example.demo.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.user-icon-dir:./user-icons}")
    private String userIconDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 例: /user-icons/user-1-xxxx.png -> file:{絶対パス}/user-1-xxxx.png
        Path dirPath = Paths.get(userIconDir).toAbsolutePath().normalize();
        String location = dirPath.toUri().toString();
        if (!location.endsWith("/")) {
            location = location + "/";
        }

        registry.addResourceHandler("/user-icons/**")
                .addResourceLocations(location)
                // 本番なら長めに、開発なら短めにするなど調整可
                .setCacheControl(CacheControl.maxAge(1, TimeUnit.HOURS).cachePublic());
    }
}