package com.SGW.Phrases.controllers

import com.SGW.Phrases.converters.HtmlConverter
import com.SGW.Phrases.models.ApiResponse
import com.SGW.Phrases.models.Author
import com.SGW.Phrases.models.Phrase
import com.SGW.Phrases.models.responses.AuthorResponse
import com.SGW.Phrases.models.responses.PhraseResponse
import com.SGW.Phrases.repositories.AuthorRepository
import com.SGW.Phrases.repositories.CategoriesRepository
import com.SGW.Phrases.repositories.PhraseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import javax.validation.Validator


@RestController
@RequestMapping("api/phrases")

@CrossOrigin(origins = "*", allowedHeaders = "*")
class PhrasesController {
    private Validator validator
    private PhraseRepository repository;
    private AuthorRepository authorRepository;
    private CategoriesRepository categoriesRepository;

    @Autowired
    PhrasesController(PhraseRepository phraseRepository,
                      AuthorRepository authorRepository,
                      CategoriesRepository categoriesRepository,
                      Validator validator) {
        repository = phraseRepository
        this.authorRepository = authorRepository
        this.categoriesRepository = categoriesRepository
        this.validator = validator
    }

    @GetMapping("/{id}")
    def getPhrase(@PathVariable("id") long id) {
        return repository.findById(id).get()
    }

    @RequestMapping(method = RequestMethod.GET)
    def index() {
        return repository.findAll()
    }

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    def indexCreatePhrase() {
        def authors = []
        def categories = []

        authorRepository
                .findAll()
                .each {
                    author ->
                        authors.add(
                                HtmlConverter
                                        .convertToOption(
                                                author.getAuthor_ID().toString(),
                                                author.getFirstName() + " " + author.getLastName()
                                        ))
                }
        categoriesRepository
                .findAll()
                .each {
                    category ->
                        categories.add(HtmlConverter
                                .convertToOption(
                                        category.getCategory_ID().toString(),
                                        category.getName().toString()
                                ))
                }

        return ResponseEntity.ok(
                [
                        "categories": categories,
                        "authors"   : authors
                ]
        )

    }



    @RequestMapping(method = RequestMethod.POST, value = "/create")
    def create(@ModelAttribute PhraseResponse formData) {
        def errors = ApiResponse.ConvertValidatorToJSON(validator.validate(formData))
        if (errors.size() > 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.ErrorMessage(errors))
        }

        Phrase phrase = new Phrase()
        phrase.setTitle(formData.getTitle())
        phrase.setMeaning(formData.getMeaning())

        phrase.setAuthor(
                authorRepository
                        .findById(
                                formData
                                        .getAuthor_id()
                                        .toLong())
                        .get()
        )
        phrase.setCategory(
                categoriesRepository
                        .findById(
                                formData
                                        .getCategory_id()
                                        .toLong())
                        .get())

        def saved = repository.save(phrase)
        return ResponseEntity.ok(
                ApiResponse.SuccessMessage("Phrase was created", saved)
        )
    }
    @PutMapping("/{id}")
    def update(@PathVariable("id") long id, @ModelAttribute PhraseResponse formData) {
        def errors = ApiResponse.ConvertValidatorToJSON(validator.validate(formData))

        if (errors.size() > 0) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.ErrorMessage(errors))
        }

        Phrase phrase = repository.findById(id).get()
        phrase.setTitle(formData.getTitle())
        phrase.setMeaning(formData.getMeaning())

        phrase.setAuthor(
                authorRepository
                        .findById(
                                formData
                                        .getAuthor_id()
                                        .toLong())
                        .get()
        )
        phrase.setCategory(
                categoriesRepository
                        .findById(
                                formData
                                        .getCategory_id()
                                        .toLong())
                        .get())

        return ResponseEntity
                .ok(ApiResponse.SuccessMessage(
                        "Phrase was update!",
                        repository.save(phrase)
                ))
    }
    @DeleteMapping("/{id}")
    def delete(@PathVariable("id") long id) {
        def phrase = repository.findById(id).get()
        repository.delete(phrase)

        return ResponseEntity.ok(phrase)
    }

}
