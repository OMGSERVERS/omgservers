package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateMatchContextHelpRequest {

    static public void validate(CreateMatchContextHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    UUID matchmaker;
    UUID match;
}
