package com.SGW.Phrases.controllers

import com.SGW.Phrases.models.ApiResponse
import com.SGW.Phrases.models.Author
import com.SGW.Phrases.models.Category
import com.SGW.Phrases.models.responses.AuthorResponse
import com.SGW.Phrases.models.responses.CategoryResponse
import com.SGW.Phrases.repositories.AuthorRepository
import com.SGW.Phrases.repositories.CategoriesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Validator

@RestController
@RequestMapping("api/categories")

@CrossOrigin(origins = "*", allowedHeaders = "*")
class CategoriesController {

    private Validator validator;
    private CategoriesRepository repository;

    @Autowired
    AuthorController(Validator validator,
                     CategoriesRepository categoriesRepository) {
        repository = categoriesRepository
        this.validator = validator
    }


    @GetMapping
    def getAllCategories() {
        return repository.findAll()
    }

    @PostMapping
    def create(@ModelAttribute CategoryResponse response) {
        def errors = ApiResponse.ConvertValidatorToJSON(validator.validate(response))

        if (errors.size() > 0)
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.ErrorMessage(errors))


        Category category = new Category()
        category.setName(response.getName())


        def res = repository.save(category)

        return ResponseEntity
                .ok(ApiResponse.SuccessMessage(
                        "Category was create", res
                ))

    }


    @GetMapping("/{id}")
    def getAuthor(@PathVariable("id") long categoryId) {
        return repository.findById(categoryId).get()
    }

    @PutMapping("/{id}")
    def update(@PathVariable("id") long id, @ModelAttribute CategoryResponse response) {
        def errors = ApiResponse.ConvertValidatorToJSON(validator.validate(response))

        if (errors.size() > 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.ErrorMessage(errors))
        }

        Category category = repository.findById(id).get()
        category.setName(response.getName())

        return ResponseEntity
                .ok(ApiResponse.SuccessMessage(
                        "Category was update!",
                        repository.save(category)
                ))
    }

    @DeleteMapping("/{id}")
    def delete(@PathVariable("id") long id) {
        def category = repository.findById(id).get()
        repository.delete(category)

        return ResponseEntity.ok(category)
    }


}
