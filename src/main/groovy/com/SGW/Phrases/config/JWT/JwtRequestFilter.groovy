package com.SGW.Phrases.config.JWT

import com.SGW.Phrases.models.users.User
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@CrossOrigin(origins = "*", allowedHeaders = "*")
class JwtRequestFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    JwtRequestFilter(JwtTokenProvider jwtTokenProvider,
                     JwtUserDetailsService jwtUserDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider
        this.jwtUserDetailsService = jwtUserDetailsService
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("authorization");
        User user = null
        String jwtToken = null
        if (!request.getServletPath().split("/").contains("auth"))
            if (request.getMethod() != "GET" && request.getMethod() != "OPTIONS") {
                println(requestTokenHeader)
                if (requestTokenHeader != null && requestTokenHeader != "") {
                    jwtToken = requestTokenHeader.substring(0);
                    try {
                        user = jwtUserDetailsService.loadUserById(jwtTokenProvider.getUserId(jwtToken).toLong())

                    } catch (IllegalArgumentException e) {
                        System.out.println("Unable to get JWT Token");
                    } catch (ExpiredJwtException e) {
                        System.out.println("JWT Token has expired");
                    }
                } else {
                    return
                }
            }

        chain.doFilter(request, response)
    }
}