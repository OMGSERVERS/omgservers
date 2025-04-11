package com.omgservers.schema.model.poolRequest;

import com.omgservers.schema.model.poolContainer.PoolContainerEnvironmentEnum;
import com.omgservers.schema.model.poolContainer.PoolContainerLabel;
import jakarta.validation.Valid;
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
        config.setVersion(PoolRequestConfigVersionEnum.V1);
        return config;
    }

    @NotNull
    PoolRequestConfigVersionEnum version;

    @Valid
    @NotNull
    ContainerConfig containerConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContainerConfig {

        @NotNull
        String image;

        @NotNull
        Map<PoolContainerLabel, String> labels;

        @NotNull
        Long cpuLimitInMilliseconds;

        @NotNull
        Long memoryLimitInMegabytes;

        @NotNull
        Map<PoolContainerEnvironmentEnum, String> environment;
    }
}
