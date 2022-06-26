package com.heldersrvio.springbootlizards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class LizardNotFoundException extends RuntimeException {
    public LizardNotFoundException(String id) {
        super("Could not find lizard " + id);
    }
}
