package com.omgservers.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteServiceAccountRequest {

    public static void validate(DeleteServiceAccountRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    String username;
}
