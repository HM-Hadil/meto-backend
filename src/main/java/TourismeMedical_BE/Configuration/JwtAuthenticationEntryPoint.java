package TourismeMedical_BE.Configuration;

import jakarta.servlet.ServletException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {




    @Override
    public void commence(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    }
}

