package com.omgservers.schema.model.poolContainer;

import com.omgservers.schema.model.poolRequest.PoolRequestConfigVersionEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolContainerConfigDto {

    static public PoolContainerConfigDto create() {
        final var poolContainerConfig = new PoolContainerConfigDto();
        poolContainerConfig.setVersion(PoolRequestConfigVersionEnum.V1);
        return poolContainerConfig;
    }

    @NotNull
    PoolRequestConfigVersionEnum version;

    @NotNull
    String imageId;

    @NotNull
    Map<PoolContainerLabel, String> labels;

    @NotNull
    Long cpuLimitInMilliseconds;

    @NotNull
    Long memoryLimitInMegabytes;

    @NotNull
    Map<PoolContainerEnvironmentEnum, String> environment;
}
