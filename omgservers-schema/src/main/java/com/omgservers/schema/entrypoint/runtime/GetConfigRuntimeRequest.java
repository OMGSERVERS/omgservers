package com.omgservers.schema.entrypoint.runtime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetConfigRuntimeRequest {

    @NotNull
    Long runtimeId;
}