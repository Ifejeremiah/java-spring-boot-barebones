package com.ifejeremiah.exception;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(String message) {
        super(message);
    }
}
