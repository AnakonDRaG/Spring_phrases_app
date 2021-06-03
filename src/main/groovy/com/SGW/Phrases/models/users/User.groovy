package com.SGW.Phrases.models.users

import com.SGW.Phrases.models.sequrity.RefreshToken

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToOne

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long user_id
    String firstName
    String lastName
    String email
    String password

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    Role role

    @OneToOne(mappedBy = 'user')
    RefreshToken refreshToken

    String getEmail() {
        return email
    }
}
