package com.innovup.meto.security.config;

import com.google.common.collect.Lists;
import com.innovup.meto.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] auth_tec_files = {"/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs", "/webjars/**"};
    private static final String[] auth_ext_files = {"/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html", "/**/*.css", "/**/*.js"};
    private static final String[] auth_public_url = {"/","/chirurgies/**","/accounts/**","/doctors/**","/admins/**","/patients/**","/dossierMedical/**","/authenticate","/api/**","/register/**", "/**/public/**", "/**/loginForm/**", "/**/VAADIN/**", "/**/vaadinServlet/**"};

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // Token based authentication instead of session based
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(auth_tec_files).permitAll()
                .antMatchers(auth_ext_files).permitAll()
                .antMatchers(auth_public_url).permitAll()
                .anyRequest().authenticated()
                //.and().authenticationProvider(authenticationProvider)
                .and().addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    private CorsConfiguration buildConfig() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //corsConfiguration.addAllowedOrigin("*");
        //  Cross domain configuration error , take .allowedOrigins Replace with .allowedOriginPatterns that will do .
        //  Set the domain name that allows cross domain requests
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        //  Set allowed methods
        corsConfiguration.addAllowedMethod("*");
        //  Whether to allow certificates
        corsConfiguration.setAllowCredentials(true);
        //  Cross domain allow time
        //corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        source.registerCorsConfiguration("/**", buildConfig());
        //configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(Collections.singletonList("http://localhost:4200"));
      //  configuration.setAllowedOrigins(List.of("));
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.PATCH);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.setAllowedHeaders(
                Lists.newArrayList(
                        "Origin","Accept", "responseType","Access-Control-Allow-Origin"
                        , "Access-Control-allow-Credentials", "Authorization",
                        "content-type", "Accept-Language"
                )
        );
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }





    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}