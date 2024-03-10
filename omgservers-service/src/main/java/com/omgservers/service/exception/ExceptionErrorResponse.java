package com.omgservers.service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionErrorResponse {

    ExceptionQualifierEnum qualifier;

    public ExceptionErrorResponse(final ServerSideBaseException e) {
        qualifier = e.getQualifier();
    }
}
