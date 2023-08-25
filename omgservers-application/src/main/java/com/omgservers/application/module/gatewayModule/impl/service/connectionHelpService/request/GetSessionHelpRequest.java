package com.omgservers.application.module.gatewayModule.impl.service.connectionHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetSessionHelpRequest {

    static public void validate(GetSessionHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long connectionId;
}
