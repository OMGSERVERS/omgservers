package com.omgservers.schema.entrypoint.developer.dto.tenantDeployment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentDetailsDto {

    DeploymentDto deployment;

    List<DeploymentLobbyResourceDto> lobbyResources;

    List<DeploymentMatchmakerResourceDto> matchmakerResources;
}
