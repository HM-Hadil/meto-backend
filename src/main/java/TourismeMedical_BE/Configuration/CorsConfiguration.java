package TourismeMedical_BE.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfiguration  {
/**public class WebSecurityConfiguration  implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
    }**/
@Bean
public WebMvcConfigurer corsConfigurer(){
    return  new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            WebMvcConfigurer.super.addCorsMappings(registry);
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowedOriginPatterns("*")
                    .allowCredentials(true);
        }

    };
}

}
