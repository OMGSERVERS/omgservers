package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.FindDeploymentMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface FindDeploymentMatchmakerAssignmentMethod {
    Uni<FindDeploymentMatchmakerAssignmentResponse> execute(ShardModel shardModel,
                                                            FindDeploymentMatchmakerAssignmentRequest request);
}
