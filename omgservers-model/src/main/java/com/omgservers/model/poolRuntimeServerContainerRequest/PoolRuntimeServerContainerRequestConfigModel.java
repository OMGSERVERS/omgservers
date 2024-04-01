package com.omgservers.model.poolRuntimeServerContainerRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolRuntimeServerContainerRequestConfigModel {

    static public PoolRuntimeServerContainerRequestConfigModel create() {
        final var config = new PoolRuntimeServerContainerRequestConfigModel();
        return config;
    }

    @NotNull
    String image;

    @NotNull
    Integer cpuLimit;

    @NotNull
    Integer memoryLimit;

    @NotNull
    Map<String, String> environment;
}
