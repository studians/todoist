package org.silentsoft.todoist.core.config.profile.dev;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Profile("dev")
@Configuration("DevWebConfig")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods(Stream.of(HttpMethod.values()).map(value -> value.name()).toArray(String[]::new)).allowedOrigins("*");
    }
}
