package org.bibliotecaviva.backend.domain.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidImportFileException extends ApiErrorException {

    public InvalidImportFileException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
