package com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment;

import com.omgservers.schema.shard.ShardedRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteDeploymentMatchmakerAssignmentRequest implements ShardedRequest {

    @Valid
    Long deploymentId;

    @Valid
    Long id;

    @Override
    public String getRequestShardKey() {
        return deploymentId.toString();
    }
}
