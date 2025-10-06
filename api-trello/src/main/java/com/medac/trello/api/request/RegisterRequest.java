package com.medac.trello.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (@NotBlank(message = "Username must not be empty")
                               @Size(min = 4, max = 250)
                               String userName,
                               @NotBlank
                               @Size(min = 4, max = 100)
                               String name,
                               @NotBlank
                               @Email
                               String email,
                               @NotBlank
                               @Size(min = 8, max = 100)
                               String password) {
}
