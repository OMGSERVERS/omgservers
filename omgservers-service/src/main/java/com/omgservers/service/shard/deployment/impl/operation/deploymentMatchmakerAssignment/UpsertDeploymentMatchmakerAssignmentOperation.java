package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertDeploymentMatchmakerAssignmentOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         DeploymentMatchmakerAssignmentModel deploymentMatchmakerAssignmentModel);
}
