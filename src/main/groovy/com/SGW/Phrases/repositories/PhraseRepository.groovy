package com.SGW.Phrases.repositories

import com.SGW.Phrases.models.Phrase
import org.springframework.data.repository.CrudRepository

interface PhraseRepository extends CrudRepository<Phrase, Long> {

}
