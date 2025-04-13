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

    @Valid
    @NotNull
    TenantVersionConfigDto config;

    @Valid
    LobbyConfigDto lobby;

    @Valid
    MatchConfigDto match;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LobbyConfigDto {

        @NotNull
        Long lobbyId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatchConfigDto {

        @NotNull
        Long matchmakerId;

        @NotNull
        Long matchId;

        @NotNull
        String mode;
    }
}
