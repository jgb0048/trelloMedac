package com.medac.trello.api.request;

import jakarta.validation.constraints.NotBlank;

public record CodeGrantRequest(
        @NotBlank String code,
        @NotBlank String scope,
        @NotBlank String authuser,
        @NotBlank String prompt) {
}
