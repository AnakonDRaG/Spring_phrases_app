package com.SGW.Phrases.controllers

import com.SGW.Phrases.repositories.AuthorRepository
import com.SGW.Phrases.repositories.PhraseRepository
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/authors")
@EnableAutoConfiguration
class AuthorController {
    private AuthorRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    String index(){
        return repository.findAll();
    }
}
