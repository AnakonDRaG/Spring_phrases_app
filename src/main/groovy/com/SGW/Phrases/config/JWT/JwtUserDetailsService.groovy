package com.SGW.Phrases.config.JWT

import com.SGW.Phrases.models.users.User
import com.SGW.Phrases.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService {
    @Autowired
    private UserRepository repository;

    User loadUserById(long id)  {
        return repository.findById(id).get()
    }
}