package com.innovup.meto.security.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig  implements WebMvcConfigurer  {
    @Override
   public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Replace with your front-end URL
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);


}
}
