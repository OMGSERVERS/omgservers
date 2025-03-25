package com.omgservers.schema.model.deploymentCommand;

import com.omgservers.schema.model.deploymentCommand.body.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeploymentCommandQualifierEnum {
    OPEN_MATCHMAKER(OpenMatchmakerDeploymentCommandBodyDto.class),
    OPEN_LOBBY(OpenLobbyDeploymentCommandBodyDto.class),
    DELETE_LOBBY(DeleteLobbyDeploymentCommandBodyDto.class),
    KICK_CLIENT(KickClientDeploymentCommandBodyDto.class),
    REMOVE_CLIENT(RemoveClientDeploymentCommandBodyDto.class);

    final Class<? extends DeploymentCommandBodyDto> bodyClass;
}
