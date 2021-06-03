package com.SGW.Phrases.config.JWT

import com.SGW.Phrases.models.users.Role
import com.SGW.Phrases.models.users.User
import com.SGW.Phrases.repositories.UserRepository
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Component


import java.nio.charset.Charset
import java.security.MessageDigest;
import java.time.ZonedDateTime;


@Component
class JwtTokenProvider {

    @Value("\${jwt.secret}")
    private String secret;

    @Value("\${jwt.expiration.minutes}")
    private long validityInMinutes;

    @Autowired
    UserRepository userRepository


    String createToken(User user) {
        Claims claims = Jwts.claims()
        setClaims(claims, user)
        //claims.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validityInMinutes).toInstant()))
        claims.setExpiration(Date.from(ZonedDateTime.now().plusSeconds(validityInMinutes).toInstant()))

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    String createRefreshToken(User user) {
        Claims claims = Jwts.claims()
        claims.setSubject(user.getUser_id().toString())
        claims.setExpiration(Date.from(ZonedDateTime.now().plusDays(5).toInstant()))

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    Authentication getAuthentication(String token) {
        User user = userRepository.findById(getUserId(token) as Long).get()
        return new UsernamePasswordAuthenticationToken(user, "");
    }

    def getUserId(String token) {
        return getAllClaimsFromToken(token).getSubject()
    }


    boolean validateToken(String token) {
        try {
            Claims claims = getAllClaimsFromToken(token)

            if (claims.getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }

    Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secret).build().parse(token).getBody() as Claims
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private List<String> getRoleNames(List<Role> userRoles) {
        List<String> result = new ArrayList<>();
        userRoles.each {role ->   result.add(role.getName())}

        return result;
    }

    private void setClaims(Claims claims, User user) {
        claims.setSubject(user.getEmail())
        claims.setSubject(user.getUser_id().toString())
    }
}