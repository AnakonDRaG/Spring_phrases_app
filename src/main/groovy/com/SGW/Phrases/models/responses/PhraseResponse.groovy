package com.SGW.Phrases.models.responses

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class PhraseResponse {
    @NotNull
    @NotEmpty
    String title

    @NotNull
    @NotEmpty
    String meaning

    @NotNull
    @NotEmpty
    String author_id

    @NotNull
    @NotEmpty
    String category_id
}
