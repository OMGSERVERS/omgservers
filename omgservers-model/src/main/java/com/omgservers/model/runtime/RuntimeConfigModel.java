package com.omgservers.model.runtime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeConfigModel {

    static public RuntimeConfigModel create() {
        final var runtimeConfig = new RuntimeConfigModel();
        return runtimeConfig;
    }

    LobbyConfig lobbyConfig;
    MatchConfig matchConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LobbyConfig {
        Long lobbyId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchConfig {
        Long matchmakerId;
        Long matchId;
    }
}
