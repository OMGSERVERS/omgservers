package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.SyncDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.module.deployment.deploymentMatchmakerAssignment.SyncDeploymentMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface SyncDeploymentMatchmakerAssignmentMethod {
    Uni<SyncDeploymentMatchmakerAssignmentResponse> execute(ShardModel shardModel,
                                                            SyncDeploymentMatchmakerAssignmentRequest request);
}
