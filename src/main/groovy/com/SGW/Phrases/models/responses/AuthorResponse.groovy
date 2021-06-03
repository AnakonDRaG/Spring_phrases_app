package com.SGW.Phrases.models.responses

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class AuthorResponse {

    String firstName

    @NotNull
    @NotEmpty
    String lastName
}
