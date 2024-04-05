package com.omgservers.model.poolRequest;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PoolRequestConfigModel {

    static public PoolRequestConfigModel create() {
        final var config = new PoolRequestConfigModel();
        return config;
    }


    ServerContainerConfig serverContainerConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServerContainerConfig {

        @NotNull
        String image;

        @NotNull
        Integer cpuLimit;

        @NotNull
        Integer memoryLimit;

        @NotNull
        Map<String, String> environment;
    }
}
