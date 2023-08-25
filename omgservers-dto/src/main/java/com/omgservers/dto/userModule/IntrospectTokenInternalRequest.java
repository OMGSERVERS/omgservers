package com.omgservers.dto.userModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectTokenInternalRequest {

    static public void validate(IntrospectTokenInternalRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    @ToString.Exclude
    String rawToken;
}
