package com.dado.quanlytailieu.config;

import com.dado.quanlytailieu.config.web.LogHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
@EnableScheduling
//@EnableJpaAuditing(auditorAwareRef = "userIdAuditorAware")
@EnableConfigurationProperties(WebProperties.class)
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final WebProperties webProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogHandlerInterceptor());
    }

    // TODO: file va image o day
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        addResourceHandler(registry, "/image/item/**", webProperties.imageItemLocation());
//        addResourceHandler(registry, "/plugins/**", webProperties.pluginsLocation());
//    }
//
//    private static void addResourceHandler(ResourceHandlerRegistry registry, String pathPattern, String location) {
//        var fileLocation = "file:///" + location.replace("\\", "/");
//        log.info("addResourceHandler pathPattern {} resourceLocation {}", pathPattern, fileLocation);
//        registry.addResourceHandler(pathPattern).addResourceLocations(fileLocation);
//    }

}
