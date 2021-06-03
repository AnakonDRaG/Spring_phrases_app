package com.SGW.Phrases.config.JWT

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
@Deprecated
class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable
{
    @Override
    void commence(HttpServletRequest request,
                  HttpServletResponse response,
                  AuthenticationException authException) throws IOException
    {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,         "Unauthorized");
    }
}