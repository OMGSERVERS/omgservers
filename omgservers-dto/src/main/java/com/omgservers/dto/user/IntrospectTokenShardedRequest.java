package com.omgservers.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectTokenShardedRequest {

    public static void validate(IntrospectTokenShardedRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    @ToString.Exclude
    String rawToken;
}
