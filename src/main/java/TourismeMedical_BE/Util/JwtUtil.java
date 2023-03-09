package TourismeMedical_BE.Util;

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

// JwtTokenUtil is a utility class that is responsible for generating and validating JWT tokens
public class JwtUtil {

    private final static  String secret_Key = "hhk";
    private final static int TOKEN_VALIDITY = 3600*5;


    public  String getUserNameFromToken(String token){
        return  getClaimFromToken(token, Claims::getSubject);

    }

    private <T> T getClaimFromToken(String token , Function<Claims , T> claimResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);

    }

    //return me claims

    private Claims getAllClaimsFromToken(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody();
    }


    private Key getSignInKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(secret_Key);
        return Keys.hmacShaKeyFor(KeyBytes);
    }
    //validate token

    public Boolean validateToken(String  token , UserDetails userDetails){
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

    public String generateToken(UserDetails userDetails){
        Map<String , Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();

    }
}
