package com.helder.springbootlizards.exception;

public class LizardNotFoundException extends RuntimeException {
    public LizardNotFoundException(String id) {
        super("Could not find lizard " + id);
    }
}
