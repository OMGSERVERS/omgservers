package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectDeploymentMatchmakerAssignmentByClientIdOperation {
    Uni<DeploymentMatchmakerAssignmentModel> execute(SqlConnection sqlConnection,
                                                     int slot,
                                                     Long deploymentId,
                                                     Long clientId);
}
