package com.SGW.Phrases.models.responses

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class RegistrationResponse {
    @NotNull
    @NotEmpty
    String firstName

    @NotNull
    @NotEmpty
    String lastName

    @NotNull
    @NotEmpty
    @Pattern(regexp=".+@.+\\..+", message="Please provide a valid email address")
    String email

    @NotNull
    @NotEmpty
    @Size(min = 4, max = 32)
    String password

    @NotNull
    @NotEmpty
    String confirmPassword

}
