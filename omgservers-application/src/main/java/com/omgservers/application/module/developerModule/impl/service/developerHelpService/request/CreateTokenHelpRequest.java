package com.omgservers.application.module.developerModule.impl.service.developerHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenHelpRequest {

    static public void validate(CreateTokenHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    UUID user;
    @ToString.Exclude
    String password;
}
