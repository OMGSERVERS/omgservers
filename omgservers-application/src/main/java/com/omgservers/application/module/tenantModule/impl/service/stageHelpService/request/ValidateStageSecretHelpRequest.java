package com.omgservers.application.module.tenantModule.impl.service.stageHelpService.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateStageSecretHelpRequest {

    static public void validateGetStageModuleRequest(ValidateStageSecretHelpRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        // TODO: validate fields
    }

    Long tenantId;
    Long stageId;
    String secret;
}
