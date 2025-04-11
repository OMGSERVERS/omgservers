package com.omgservers.schema.model.lobby;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LobbyConfigDto {

    static public LobbyConfigDto create() {
        final var lobbyConfig = new LobbyConfigDto();
        lobbyConfig.setVersion(LobbyConfigVersionEnum.V1);
        return lobbyConfig;
    }

    @NotNull
    LobbyConfigVersionEnum version;
}
