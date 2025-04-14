package com.omgservers.schema.shard.deployment.deployment.dto;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentDataDto {

    @NotNull
    DeploymentModel deployment;

    @NotNull
    List<DeploymentLobbyResourceModel> lobbyResources;

    @NotNull
    List<DeploymentMatchmakerResourceModel> matchmakerResources;
}
