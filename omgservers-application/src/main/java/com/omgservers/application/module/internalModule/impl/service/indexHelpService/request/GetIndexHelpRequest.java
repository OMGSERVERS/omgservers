package com.omgservers.application.module.internalModule.impl.service.indexHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIndexHelpRequest {

    static public void validate(GetIndexHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    String name;
}
