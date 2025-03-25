package com.omgservers.schema.module.deployment.deploymentLobbyAssignment;

import com.omgservers.schema.module.ShardedRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDeploymentLobbyAssignmentRequest implements ShardedRequest {

    @Valid
    Long deploymentId;

    @Valid
    Long id;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
