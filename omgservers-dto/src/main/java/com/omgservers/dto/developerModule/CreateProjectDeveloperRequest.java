package com.omgservers.dto.developerModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectDeveloperRequest {

    static public void validate(CreateProjectDeveloperRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
}
