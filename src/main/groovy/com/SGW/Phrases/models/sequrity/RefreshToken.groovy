package com.SGW.Phrases.models.sequrity

import com.SGW.Phrases.models.users.User
import org.springframework.format.annotation.DateTimeFormat

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.OneToOne


@Entity
class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user

    @Column(unique = true)
    String token



}
