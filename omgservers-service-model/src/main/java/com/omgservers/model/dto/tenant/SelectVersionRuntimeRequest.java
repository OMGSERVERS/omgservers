package com.omgservers.model.dto.tenant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SelectVersionRuntimeRequest {

    @NotNull
    Long tenantId;

    @NotNull
    Long versionId;

    @NotNull
    Strategy strategy;

    public enum Strategy {
        RANDOM,
    }
}
