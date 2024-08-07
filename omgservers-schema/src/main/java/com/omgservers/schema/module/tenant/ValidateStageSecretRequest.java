package com.omgservers.schema.module.tenant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateStageSecretRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long stageId;

    @NotBlank
    String secret;
}
