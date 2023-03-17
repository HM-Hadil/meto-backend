package com.innovup.meto.users.controller;

import com.innovup.meto.security.auth.AuthenticationRequest;
import com.innovup.meto.security.auth.AuthenticationResponse;
import com.innovup.meto.security.providers.JwtTokenProvider;
import com.innovup.meto.security.service.CustomUserDetailsServiceImpl;
import com.innovup.meto.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final String SECRET_KEY = "556A586E327235753878214125442A472D4B6150645367566B59703373367639";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenProvider jwtUtils;
    @Autowired
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String jwtToken = jwtUtils.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwtToken));

        }
    }




    /**  HashMap<String, Object> response = new HashMap<>();

     Optional<User> userFromDB = userRepository.findByEmail(request.getEmail());

     if (!userFromDB.isPresent()) {
     response.put("message", "Email not found !");
       return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
     }

     else {


     Boolean compare = this.bCryptPasswordEncoder.matches(request.getPassword(), userFromDB.get().getPassword());

     if (!compare) {
     response.put("message", "Password not found !");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
     }

     }
     String token = Jwts.builder()
     .claim("data", userFromDB)
     .signWith(getSignInKey(), SignatureAlgorithm.HS256)                        .compact();
     response.put("token", token);

     return ResponseEntity.status(HttpStatus.OK).body(response);**/

  /**  private Key getSignInKey() {
        var bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }*/




