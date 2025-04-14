package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.DeleteDeploymentMatchmakerAssignmentResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDeploymentMatchmakerAssignmentMethod {
    Uni<DeleteDeploymentMatchmakerAssignmentResponse> execute(ShardModel shardModel,
                                                              DeleteDeploymentMatchmakerAssignmentRequest request);
}
