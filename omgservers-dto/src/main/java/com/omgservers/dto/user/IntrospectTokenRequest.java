package com.omgservers.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectTokenRequest {

    public static void validate(IntrospectTokenRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    @ToString.Exclude
    String rawToken;
}
