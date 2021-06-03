package com.SGW.Phrases.models

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany


@Entity
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Category_ID
    String Name

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Category", orphanRemoval = true)
    private Set<Phrase> phrases
}
