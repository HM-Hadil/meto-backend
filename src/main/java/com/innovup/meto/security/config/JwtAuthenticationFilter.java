package com.innovup.meto.security.config;

import com.innovup.meto.security.UserPrincipal;
import com.innovup.meto.security.providers.JwtTokenProvider;
import com.innovup.meto.security.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String jwtToken = header.substring(7);

            try {
                String userEmail = jwtTokenProvider.getUserNameFromToken(jwtToken);
                UserPrincipal userPrincipal = (UserPrincipal) customUserDetailsService.loadUserByUsername(userEmail);

                if (Boolean.TRUE.equals(jwtTokenProvider.isTokenValid(jwtToken, userPrincipal))) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
                filterChain.doFilter(request, response);

            } catch (IllegalArgumentException e) {
                log.info("Unable to get the JWT token");

            } catch (ExpiredJwtException e) {
                log.info("JWT Token has expired");
            }

        } else {
            log.info("JWT Token does not begin with Bearer String");

            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PATCH, DELETE, PUT, OPTIONS");
            response.setHeader("Access-Control-Allow-Headers", "*");
            request.setAttribute("No-Auth", "true");

            filterChain.doFilter(request, response);
        }
    }
}
