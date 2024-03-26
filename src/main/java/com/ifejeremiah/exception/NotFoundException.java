package com.ifejeremiah.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
