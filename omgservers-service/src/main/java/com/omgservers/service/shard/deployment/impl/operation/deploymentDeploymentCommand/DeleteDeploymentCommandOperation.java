package com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteDeploymentCommandOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         Long deploymentId,
                         Long id);
}
