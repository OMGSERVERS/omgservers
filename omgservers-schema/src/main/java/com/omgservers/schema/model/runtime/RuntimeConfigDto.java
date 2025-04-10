package com.omgservers.schema.model.runtime;

import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuntimeConfigDto {

    static public RuntimeConfigDto create(final TenantVersionConfigDto tenantVersionConfig) {
        final var runtimeConfig = new RuntimeConfigDto();
        runtimeConfig.setVersion(RuntimeConfigVersionEnum.V1);
        runtimeConfig.setConfig(tenantVersionConfig);
        return runtimeConfig;
    }

    @NotNull
    RuntimeConfigVersionEnum version;

    LobbyConfigDto lobby;
    MatchConfigDto match;

    @Valid
    @NotNull
    TenantVersionConfigDto config;

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
