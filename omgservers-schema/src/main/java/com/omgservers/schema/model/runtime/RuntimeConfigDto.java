package com.omgservers.schema.model.runtime;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeConfigDto {

    static public RuntimeConfigDto create() {
        final var runtimeConfig = new RuntimeConfigDto();
        return runtimeConfig;
    }

    LobbyConfigDto lobbyConfig;
    MatchConfigDto matchConfig;
    TenantVersionConfigDto versionConfig;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LobbyConfigDto {
        Long lobbyId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchConfigDto {
        Long matchmakerId;
        Long matchId;
    }
}
