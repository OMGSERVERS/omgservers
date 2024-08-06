package com.omgservers.schema.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionErrorResponse {

    ExceptionQualifierEnum qualifier;
}
