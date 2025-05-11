package org.example.vofasbackendv1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with field: %s and value %s already exist!", resourceName, fieldName, fieldValue));
    }
}



