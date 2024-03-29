package com.omgservers.model.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerConfigModel {

    static public ServerConfigModel create() {
        final var serverConfig = new ServerConfigModel();
        return serverConfig;
    }

    DockerHostConfig dockerHostConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DockerHostConfig {
        Integer maxContainers;
    }
}
