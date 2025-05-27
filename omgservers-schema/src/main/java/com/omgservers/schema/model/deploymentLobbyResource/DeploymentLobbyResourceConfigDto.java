package com.omgservers.schema.model.deploymentLobbyResource;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import com.omgservers.schema.model.lobby.LobbyConfigDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentLobbyResourceConfigDto {

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    DeploymentLobbyResourceConfigVersionEnum version = DeploymentLobbyResourceConfigVersionEnum.V1;

    @NotNull
    @JsonSetter(nulls = Nulls.SKIP)
    LobbyConfigDto lobbyConfig = new LobbyConfigDto();
}
