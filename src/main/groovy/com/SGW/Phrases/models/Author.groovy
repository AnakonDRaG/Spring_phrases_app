package com.SGW.Phrases.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Author_ID;
    String FirstName;
    String LastName;

}
