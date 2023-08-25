package com.omgservers.dto.internalModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceAccountHelpRequest {

    static public void validate(GetServiceAccountHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
    }

    String username;
}
