package com.SGW.Phrases.controllers.auth

import com.SGW.Phrases.config.JWT.JwtTokenProvider
import com.SGW.Phrases.config.JWT.JwtUtil
import com.SGW.Phrases.models.ApiResponse
import com.SGW.Phrases.models.users.Role
import com.SGW.Phrases.models.users.User
import com.SGW.Phrases.repositories.RefreshTokenRepository
import com.SGW.Phrases.repositories.RoleRepository
import com.SGW.Phrases.repositories.UserRepository
import io.jsonwebtoken.Claims
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
class AuthUserController {

    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository
    private RoleRepository roleRepository
    private RefreshTokenRepository refreshTokenRepository

    @Autowired
    AuthUserController(UserRepository userRepository,
                       RoleRepository roleRepository,
                       JwtTokenProvider jwtTokenProvider,
                       RefreshTokenRepository refreshTokenRepository
    ) {
        this.userRepository = userRepository
        this.roleRepository = roleRepository
        this.jwtTokenProvider = jwtTokenProvider
        this.refreshTokenRepository = refreshTokenRepository
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/validate")
    @Deprecated
    def isAuth(@RequestParam(value = "accessToken") String token) {
        println(jwtTokenProvider.validateToken(token))
    }

    @RequestMapping(method = RequestMethod.POST, value = "/token/refresh")
    def userReAuth(@RequestParam(value = "accessToken") String accessToken,
                   @RequestParam(value = "refreshToken") String refreshToken) {

        println(jwtTokenProvider.validateToken(accessToken))
        Claims accessTokenDecoded = jwtTokenProvider.getAllClaimsFromToken(accessToken)
        Claims refreshTokenDecoded = jwtTokenProvider.getAllClaimsFromToken(refreshToken)


        return ""
        println(accessTokenDecoded.getSubject())
        User user = userRepository.findById(refreshTokenDecoded.getSubject().toLong()).get()

        if (accessTokenDecoded.getSubject() != refreshTokenDecoded.getSubject()
                || refreshToken.isEmpty()
                || refreshToken == null
        ) {
            def _token = refreshTokenRepository.findByUser(user)
            _token.setToken(null)
            refreshTokenRepository.save(_token)
            return ResponseEntity.badRequest().body(ApiResponse.ErrorMessage([refrestToken: "Error!"]))
        }


        if (user.getRefreshToken().getToken() == refreshToken)
        {
            println("wqeqwe")
            return ResponseEntity.ok(ApiResponse.SuccessMessage(
                    "Token was refresh!",
                    [
                            accessToken : jwtTokenProvider.createRefreshToken(user),
                            refreshToken: refreshToken
                    ]
            ))
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    def getUser(@RequestParam(value = "accessToken") token) {
        Claims claims = jwtTokenProvider.getAllClaimsFromToken(token)

        User user = userRepository.findById(claims.getSubject().toLong()).get()

        return [
                firstName: user.getFirstName(),
                lastName : user.getLastName(),
                email    : user.getEmail(),
                role     : user.getRole().getRole()
        ]
    }


}
