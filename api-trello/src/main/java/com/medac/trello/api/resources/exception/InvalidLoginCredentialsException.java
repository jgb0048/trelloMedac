package com.medac.trello.api.resources.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ResponseStatus(value= UNAUTHORIZED, reason="Invalid login credentials")  // 404
public class InvalidLoginCredentialsException extends RuntimeException {
}
