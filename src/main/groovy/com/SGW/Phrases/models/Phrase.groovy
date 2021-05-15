package com.SGW.Phrases.models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Phrase_ID;

    String Title;
    String Meaning;

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "Category_ID")
    //Category Category;

    @ManyToOne
    @JoinColumn(name = "Author_ID")
    Author Author;

}
