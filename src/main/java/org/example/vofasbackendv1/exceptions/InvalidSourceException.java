package org.example.vofasbackendv1.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
@Setter
public class InvalidSourceException extends RuntimeException {
    public InvalidSourceException(String resourceName, String errors) {
        super(String.format("Bad Request for source %s. Fix following errors %s", resourceName, errors));
    }

}

