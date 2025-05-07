package org.example.vofasbackendv1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class InvalidTokenException extends RuntimeException {
  public InvalidTokenException(String token) {
    super(String.format("Given token %s not valid", token));
  }}

