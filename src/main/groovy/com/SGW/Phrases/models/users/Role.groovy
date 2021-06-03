package com.SGW.Phrases.models.users

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne

@Entity
class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long role_ID

    String role
    String name
}
