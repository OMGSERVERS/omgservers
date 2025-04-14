package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.GetDeploymentMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface GetDeploymentMatchmakerAssignmentMethod {
    Uni<GetDeploymentMatchmakerAssignmentResponse> execute(ShardModel shardModel,
                                                           GetDeploymentMatchmakerAssignmentRequest request);
}
