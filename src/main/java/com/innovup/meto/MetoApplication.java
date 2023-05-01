package com.innovup.meto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@SpringBootApplication
@EntityScan(basePackages = {"com.innovup.meto.*"})
@EnableJpaRepositories(basePackages = {"com.innovup.meto.*"})
@PropertySource(encoding = "UTF-8", value = {"application.properties", "datasource.properties", "mail.properties"})
public class MetoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MetoApplication.class, args);
    }

}
