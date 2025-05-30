package com.omgservers.schema.model.deploymentCommand.body;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandBodyDto;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OpenLobbyDeploymentCommandBodyDto extends DeploymentCommandBodyDto {

    @NotNull
    Long lobbyId;

    @Override
    public DeploymentCommandQualifierEnum getQualifier() {
        return DeploymentCommandQualifierEnum.OPEN_LOBBY;
    }
}
