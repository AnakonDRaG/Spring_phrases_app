package com.SGW.Phrases.config.JWT

import com.SGW.Phrases.models.users.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.JwtBuilder
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.io.Decoder
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Component
import java.time.ZonedDateTime;

@Component
@Deprecated
class JwtUtil {

    private @Value("\${jwt.secret}")
    String secret;

    @Value("\${jwt.expiration.minutes}")
    private long validityInMinutes;

    User parseToken(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            User u = new User();
            u.setUsername(body.getSubject());
            u.setId(Long.parseLong((String) body.get("userId")));
            return u;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }

    String generateToken(User user) {
        Claims claims = Jwts.claims()
        setClaims(claims, user)
        claims.setExpiration(Date.from(ZonedDateTime.now().plusMinutes(validityInMinutes).toInstant()))

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    void debugToken(String token) {

    }

    String generateRefreshToken(String token, User user) {
        Claims claims  = getAllClaimsFromToken(token)

        return Jwts.builder().setClaims(claims).setSubject(claims.getSubject()).setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(2).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }

    def decodeToken(String token){
        def claims = getAllClaimsFromToken(token)
        return claims;
    }

    def validateToken(String authToken) {
        try {
            def claims = Jwts.parserBuilder().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw ex;
        }
    }

    Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder().setSigningKey(secret).build().parse(token).body as Claims
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }


}
