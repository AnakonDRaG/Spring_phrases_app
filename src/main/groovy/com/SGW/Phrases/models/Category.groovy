package com.SGW.Phrases.models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Category_ID;
    String Name;

}
