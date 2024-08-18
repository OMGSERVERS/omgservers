package com.omgservers.schema.model.poolSeverContainer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolServerContainerConfigDto {

    static public PoolServerContainerConfigDto create() {
        final var poolServerContainerConfig = new PoolServerContainerConfigDto();
        return poolServerContainerConfig;
    }

    @NotNull
    String imageId;

    @NotNull
    Integer cpuLimitInMilliseconds;

    @NotNull
    Integer memoryLimitInMegabytes;

    @NotNull
    Map<String, String> environment;
}
