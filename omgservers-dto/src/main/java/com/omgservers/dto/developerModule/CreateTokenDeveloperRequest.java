package com.omgservers.dto.developerModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTokenDeveloperRequest {

    static public void validate(CreateTokenDeveloperRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long userId;
    @ToString.Exclude
    String password;
}
