package com.SGW.Phrases.controllers.auth

import com.SGW.Phrases.config.JWT.JwtTokenProvider
import com.SGW.Phrases.config.JWT.JwtUtil
import com.SGW.Phrases.models.ApiResponse
import com.SGW.Phrases.models.responses.LoginResponse
import com.SGW.Phrases.models.responses.RegistrationResponse
import com.SGW.Phrases.models.sequrity.RefreshToken
import com.SGW.Phrases.models.users.Role
import com.SGW.Phrases.models.users.User
import com.SGW.Phrases.repositories.RefreshTokenRepository
import com.SGW.Phrases.repositories.RoleRepository
import com.SGW.Phrases.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestAttribute

import javax.validation.Validator
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
class AuthController {

    private Validator validator
    private UserRepository userRepository

    private RefreshTokenRepository refreshTokenRepository
    private PasswordEncoder passwordEncoder

    @Autowired
    private RoleRepository roleRepository

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthController(Validator validator,
                   UserRepository userRepository,
                   PasswordEncoder passwordEncoder,
                   RefreshTokenRepository refreshTokenRepository) {
        this.validator = validator
        this.userRepository = userRepository
        this.passwordEncoder = passwordEncoder
        this.refreshTokenRepository = refreshTokenRepository
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    def loginUser(@ModelAttribute LoginResponse formData) {
        def errors = ApiResponse.ConvertValidatorToJSON(validator.validate(formData))

        if (errors.size() > 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.ErrorMessage(errors))
        } else {
            def user = userRepository.findByEmail(formData.email)
            if (user == null)
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body([errors: [email: "User not found!"]])

            if(!passwordEncoder.matches(formData.password, user.getPassword()))
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body([errors: [password: "Password don`t matches!"]])

            return auth(user)
        }


    }

    @RequestMapping(method = RequestMethod.POST, value = "/logout")
    def logout(@RequestParam(value="accessToken") String token) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    def registerUser(@ModelAttribute RegistrationResponse formData) {
        def errors = ApiResponse.ConvertValidatorToJSON(validator.validate(formData))
        if (errors.size() > 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.ErrorMessage(errors))
        }
        else{
            if(userRepository.findByEmail(formData.email) != null)
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body([errors: [email: "The current email address is in use!"]])

            if(formData.confirmPassword != formData.password)
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body([errors: [confirmPassword: "Password doesn't`t match!"]])

            return register(formData)
        }


    }

    private def register(RegistrationResponse formData){
        User user = new User();
        user.setEmail(formData.getEmail())
        user.setFirstName(formData.getFirstName())
        user.setLastName(formData.getLastName())
        user.setPassword(passwordEncoder.encode(formData.password))

        Role role = roleRepository.findById(1 as Long).get()
        user.setRole(role)

        RefreshToken refreshToken = new RefreshToken()
        refreshToken.setUser(userRepository.save(user))

        User userRes = userRepository.findById(user.user_id).get()
        userRes.setRefreshToken(refreshTokenRepository.save(refreshToken))


        User result = userRepository.save(userRes)

        return ResponseEntity.ok(ApiResponse.SuccessMessage("User was created", auth(result)))
    }

    private def auth(User user) {

        String accessToken = jwtTokenProvider.createToken(user)
        String refreshToken = jwtTokenProvider.createRefreshToken(user)

        def refreshTokenModel = refreshTokenRepository.findById(user.getRefreshToken().getId()).get()
        refreshTokenModel.setToken(refreshToken)
        refreshTokenModel.setUser(user)

        refreshTokenRepository.save(refreshTokenModel)

        return ResponseEntity
                .ok(ApiResponse.SuccessMessage(
                        "Login was success!",
                        [
                                accessToken : accessToken,
                                refreshToken: refreshToken
                        ]
                ))
    }


}
