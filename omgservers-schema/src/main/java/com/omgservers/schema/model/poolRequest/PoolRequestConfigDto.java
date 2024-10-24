package com.omgservers.schema.model.poolRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolRequestConfigDto {

    static public PoolRequestConfigDto create() {
        final var config = new PoolRequestConfigDto();
        return config;
    }

    ContainerConfig containerConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainerConfig {

        @NotNull
        String imageId;

        @NotNull
        Long cpuLimitInMilliseconds;

        @NotNull
        Long memoryLimitInMegabytes;

        @NotNull
        Map<String, String> environment;
    }
}
