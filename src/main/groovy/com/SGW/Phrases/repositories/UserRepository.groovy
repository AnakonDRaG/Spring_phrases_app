package com.SGW.Phrases.repositories

import com.SGW.Phrases.models.users.User
import org.springframework.data.repository.CrudRepository

interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}