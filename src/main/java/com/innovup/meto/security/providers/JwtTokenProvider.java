package com.innovup.meto.security.providers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "556A586E327235753878214125442A472D4B6150645367566B59703373367639";


    public  String getUserNameFromToken(String token){
        return  getClaimFromToken(token, Claims::getSubject);

    }
    private <T> T getClaimFromToken(String token , Function<Claims , T> claimResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);

    }
    private Claims getAllClaimsFromToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody();
    }

    public  String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    //generate token
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))//check if token still valid or not
                .setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60*24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Boolean isTokenValid(String  token , UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public  Boolean isTokenExpired(String token){
        final Date  expirationDate =  getExpirationDateFromToken(token);
        return expirationDate.before(new Date());

    }

    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token , Claims::getExpiration);
    }

    private Key getSignInKey() {
        var bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

}
