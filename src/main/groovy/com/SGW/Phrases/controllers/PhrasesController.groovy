package com.SGW.Phrases.controllers

import com.SGW.Phrases.models.Phrase
import com.SGW.Phrases.repositories.PhraseRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/phrases")
@EnableAutoConfiguration
class PhrasesController {
    private PhraseRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    String index(){
        return repository.findAll();
    }


    @RequestMapping(method = RequestMethod.POST)
    Phrase create(@RequestBody Phrase phrase){
        return repository.save(phrase);
    }


}
