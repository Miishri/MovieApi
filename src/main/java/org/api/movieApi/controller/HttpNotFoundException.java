package org.api.movieApi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Value Not Found")
public class HttpNotFoundException extends Exception {
    public HttpNotFoundException() {
    }

    public HttpNotFoundException(String message) {
        super(message);
    }

    public HttpNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpNotFoundException(Throwable cause) {
        super(cause);
    }

    public HttpNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}