package com.omgservers.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionErrorResponse {

    String id;
    String message;

    public ExceptionErrorResponse(Throwable t) {
        id = t.getClass().getSimpleName();
        message = t.getMessage();
    }
}
