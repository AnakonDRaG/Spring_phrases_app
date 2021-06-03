package com.SGW.Phrases.models.responses

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

class LoginResponse {
    @NotNull
    @NotEmpty
    @Email
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    String email

    @NotNull
    @NotEmpty
    String password
}
