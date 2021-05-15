package com.SGW.Phrases.repositories

import com.SGW.Phrases.models.Author
import org.springframework.data.repository.CrudRepository

interface AuthorRepository extends CrudRepository<Author, Long> {

}
