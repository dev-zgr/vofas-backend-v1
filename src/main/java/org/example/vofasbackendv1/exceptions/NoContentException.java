package org.example.vofasbackendv1.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoContentException extends RuntimeException {

    public NoContentException(String resourceName, String pageNumber) {
        super(String.format("No content available for %s: page %s", resourceName, pageNumber));
    }
}
