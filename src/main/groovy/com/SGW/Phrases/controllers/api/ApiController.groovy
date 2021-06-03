package com.SGW.Phrases.controllers.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class ApiController {

    @GetMapping
    def index() {
        return [title:"Catch phrases"]
    }
}
