package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectDeploymentMatchmakerAssignmentOperation {
    Uni<DeploymentMatchmakerAssignmentModel> execute(SqlConnection sqlConnection,
                                                     int shard,
                                                     Long deploymentId,
                                                     Long id);
}
