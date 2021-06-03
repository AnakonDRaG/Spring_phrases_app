package com.SGW.Phrases.repositories

import com.SGW.Phrases.models.Category
import org.springframework.data.repository.CrudRepository

interface CategoriesRepository extends CrudRepository<Category, Long> {

}