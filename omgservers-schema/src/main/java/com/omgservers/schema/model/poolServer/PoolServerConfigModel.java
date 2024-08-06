package com.omgservers.schema.model.poolServer;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolServerConfigModel {

    static public PoolServerConfigModel create() {
        final var serverConfig = new PoolServerConfigModel();
        return serverConfig;
    }

    DockerHostConfig dockerHostConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DockerHostConfig {

        @NotNull
        URI dockerDaemonUri;

        @NotNull
        Integer cpuCount;

        @NotNull
        Integer memorySize;

        @NotNull
        Integer maxContainers;
    }
}
