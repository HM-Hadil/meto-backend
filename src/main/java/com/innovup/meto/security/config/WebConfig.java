package com.innovup.meto.security.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig  implements WebMvcConfigurer  {
    @Override
   public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:4200") // Replace with your front-end URL
                .allowedMethods("*")
                .allowedHeaders("*");
               /** .allowCredentials(true)
                .exposedHeaders("Authorization")
                .allowedHeaders("Content-Type")
                .maxAge(3600L);**/


}
}
