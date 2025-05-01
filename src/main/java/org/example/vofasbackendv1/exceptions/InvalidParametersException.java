package org.example.vofasbackendv1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidParametersException extends RuntimeException {

    public InvalidParametersException(String message) {
        super(message);
    }

    public InvalidParametersException(String parameterName, String reason) {
        super(String.format("Invalid value for parameter '%s': %s", parameterName, reason));
    }
}


