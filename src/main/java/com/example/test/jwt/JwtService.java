package com.example.test.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    String SECRET_KEY;
    @Value("${jwt.expired}")
    Long expired;
    public String generateToken(UserDetails userDetails){
       return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expired))
                .signWith(getSignInKey())
                .addClaims(getClaims(userDetails))
                .compact();
    }
    public Claims parse(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token).getBody();
    }
        private Map<String, Object> getClaims(UserDetails userDetails){
          return   Map.of(
                    "roles",
                    userDetails.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList())
            );
        }
    public String getUsernameInToken(String token){
        return parse(token).getSubject();
    }
    public Boolean tokenIsExpired(String token){
        return parse(token).getExpiration().after(new Date(System.currentTimeMillis()));
    }
    private Key getSignInKey(){
        byte[] decode = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(decode);
    }
}
