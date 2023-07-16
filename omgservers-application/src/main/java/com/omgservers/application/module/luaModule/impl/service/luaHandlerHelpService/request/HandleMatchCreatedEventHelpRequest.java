package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HandleMatchCreatedEventHelpRequest {

    static public void validate(final HandleMatchCreatedEventHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    UUID matchmaker;
    UUID uuid;
}
