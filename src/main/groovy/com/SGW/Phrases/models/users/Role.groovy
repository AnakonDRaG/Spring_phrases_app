package com.SGW.Phrases.models.users

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Role_ID;

    private String role;
}
