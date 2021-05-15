package com.SGW.Phrases.models.users

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.OneToMany

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long User_ID;
    String firstName;
    String lastName;
    String email;
    String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "User_ID", referencedColumnName = "User_ID"))
    Set<Role> role;
}
