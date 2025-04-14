package com.omgservers.service.shard.deployment.impl.service.deploymentService.impl.method.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsRequest;
import com.omgservers.schema.shard.deployment.deploymentMatchmakerAssignment.ViewDeploymentMatchmakerAssignmentsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewDeploymentMatchmakerAssignmentMethod {
    Uni<ViewDeploymentMatchmakerAssignmentsResponse> execute(ShardModel shardModel,
                                                             ViewDeploymentMatchmakerAssignmentsRequest request);
}
