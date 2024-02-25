package com.Urban_India.security;

import com.Urban_India.exception.UrbanApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import java.security.Key;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDay;

    // generate token

    public String generateToken(Authentication authentication){

        String userName=authentication.getName();
        Date currDate=new Date();
        Date expireDate=new Date(currDate.getTime() + jwtExpirationDay);

        String token  =  Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key(),SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public String generateToken(String email){

        String userName=email;
        Date currDate=new Date();
        Date expireDate=new Date(currDate.getTime() + jwtExpirationDay);

        String token  =  Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key(),SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from token
    public String getusername(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        String username=claims.getSubject();
        return username;
    }

    public boolean validateToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        }catch (MalformedJwtException exception){
            throw new UrbanApiException(HttpStatus.BAD_REQUEST,"Invalid JWT Token");
        }catch (ExpiredJwtException exception){
            throw new UrbanApiException(HttpStatus.BAD_REQUEST,"Expired JWT Token");
        }catch (UnsupportedJwtException exception){
            throw new UrbanApiException(HttpStatus.BAD_REQUEST,"Unsupported JWT Token");
        }catch (IllegalArgumentException exception){
            throw new UrbanApiException(HttpStatus.BAD_REQUEST,"JWT Claim String is Empty");
        }
    }
}
