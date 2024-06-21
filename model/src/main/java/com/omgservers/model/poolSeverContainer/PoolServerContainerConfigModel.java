package com.omgservers.model.poolSeverContainer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolServerContainerConfigModel {

    static public PoolServerContainerConfigModel create() {
        final var poolServerContainerConfig = new PoolServerContainerConfigModel();
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
