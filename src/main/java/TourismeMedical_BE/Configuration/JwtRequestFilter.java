package TourismeMedical_BE.Configuration;

import TourismeMedical_BE.Service.JwtService;
import TourismeMedical_BE.Util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter implements Filter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    JwtService jwtService;

    @Override
    protected void doFilterInternal(javax.servlet.http.HttpServletRequest request,
                                    javax.servlet.http.HttpServletResponse response,
                                    javax.servlet.FilterChain filterChain)
            throws javax.servlet.ServletException, IOException {




        //take the header from the request
   final String header =request.getHeader("Authorization");

   String jwtToken = null;
   String userName = null;

   if (header != null && header.startsWith("Bearer")){
       //retrieve the JWT token after the  7 position
      jwtToken = header.substring(7);

      try { //retrieve the username from the Jwt token
       userName = jwtUtil.getUserNameFromToken(jwtToken);

      }
      //error jwt
      catch (IllegalArgumentException e){
          System.out.println("unable to get the jwt token");
      }
      catch (ExpiredJwtException e) {
          System.out.println("JWT Token has expired");
      }
   } else {
       System.out.println("JWT Token does not begin with Bearer String");
   }

   if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
       UserDetails userDetails = jwtService.loadUserByUsername(userName);
       //validate token

       if (jwtUtil.validateToken(jwtToken , userDetails)){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
        =  new UsernamePasswordAuthenticationToken (userDetails , null ,
                   userDetails.getAuthorities());
           usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
         SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
       }
   }
   //dofilter : check the username and jwt token
   filterChain.doFilter(request, response);

}


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}