package com.omgservers.router.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouterErrorResponse {

    RouterExceptionQualifierEnum qualifier;
}
