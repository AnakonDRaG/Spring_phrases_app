package com.SGW.Phrases.services

import com.SGW.Phrases.models.ApiResponse
import com.SGW.Phrases.models.users.User
import com.SGW.Phrases.repositories.UserRepository
import com.SGW.Phrases.sequrity.TokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/auth")
class AuthService {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/login")
    def test(){
        return {test:30}
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    def loginUser(@RequestParam Map<String,String> allParams){
        println allParams["username"]
        return [token:"okay"]
    }

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    def registerUser(@RequestParam Map<String,String> allParams) {

        User user = new User();
        user.setEmail(allParams['email'])
        user.setFirstName(allParams['firstname'])
        user.setLastName(allParams['lastname'])
        user.setPassword(allParams['password'])


        //User result = userRepository.save(user)

        return ApiResponse.SuccessMessage("User was created", user)
    }
}
