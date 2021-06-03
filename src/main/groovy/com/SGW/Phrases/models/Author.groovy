package com.SGW.Phrases.models

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long author_ID;
    String firstName;
    String lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Author", orphanRemoval = true)
    private Set<Phrase> phrases
}
