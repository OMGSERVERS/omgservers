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
public class PoolServerConfigDto {

    static public PoolServerConfigDto create() {
        final var poolServerConfig = new PoolServerConfigDto();
        return poolServerConfig;
    }

    /**
     * The service server to be used for communication with this PoolServer.
     */
    @NotNull
    URI serverUri;

    /**
     * The URI for runtimes within this server to connect to the service.
     */
    @NotNull
    URI serviceUri;

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
