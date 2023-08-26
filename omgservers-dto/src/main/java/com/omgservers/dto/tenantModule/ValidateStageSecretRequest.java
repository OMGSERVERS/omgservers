package com.omgservers.dto.tenantModule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateStageSecretRequest {

    static public void validateGetStageModuleRequest(ValidateStageSecretRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
    Long stageId;
    String secret;
}
