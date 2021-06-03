package com.SGW.Phrases.repositories

import com.SGW.Phrases.models.sequrity.RefreshToken
import com.SGW.Phrases.models.users.User
import org.springframework.data.repository.CrudRepository

interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    RefreshToken findByUser(User user)
}
