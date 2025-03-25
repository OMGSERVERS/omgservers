package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerAssignment;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteDeploymentMatchmakerAssignmentOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         Long deploymentId,
                         Long id);
}
