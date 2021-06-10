package com.SGW.Phrases.models

import org.hibernate.annotations.Cascade

import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Phrase_ID;

    String Title;
    String Meaning;

    @ManyToOne
    @JoinColumn(name = "Author_ID")
    Author Author;


    @ManyToOne
    @JoinColumn(name = "Category_ID")
    Category Category;
}
