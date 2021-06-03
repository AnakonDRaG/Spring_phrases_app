package com.SGW.Phrases.repositories

import com.SGW.Phrases.models.users.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository extends CrudRepository<Role, Long> {

}