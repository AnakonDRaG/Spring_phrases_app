package com.SGW.Phrases.models.responses

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CategoryResponse {
    @NotNull
    @NotEmpty
    String name
}
