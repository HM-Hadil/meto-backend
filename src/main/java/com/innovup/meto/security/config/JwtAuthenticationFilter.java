package com.innovup.meto.security.config;

import com.innovup.meto.security.providers.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {


       // response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");

        //take the header from the request
        final String header =request.getHeader("Authorization");

        String jwtToken = null;
        String userEmail = null;

        if (header != null && header.startsWith("Bearer ")){
            //retrieve the JWT token after the  7 position
            jwtToken = header.substring(7);

            try { //retrieve the username from the Jwt token
                userEmail = jwtTokenProvider.getUserNameFromToken(jwtToken);

            }
            //error jwt
            catch (IllegalArgumentException e){
                log.info("unable to get the jwt token");
            }
            catch (ExpiredJwtException e) {
                log.info("JWT Token has expired");
            }
        } else {
            log.info("JWT Token does not begin with Bearer String");
        }


        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            //validate token

            if (jwtTokenProvider.isTokenValid(jwtToken , userDetails)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        =  new UsernamePasswordAuthenticationToken (userDetails , null ,
                        userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        //dofilter : check the username and jwt token
        filterChain.doFilter(request, response);



    }
}
