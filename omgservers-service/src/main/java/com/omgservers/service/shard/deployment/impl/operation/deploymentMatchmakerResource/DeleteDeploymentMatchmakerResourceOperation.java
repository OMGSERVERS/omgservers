package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface DeleteDeploymentMatchmakerResourceOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         Long deploymentId,
                         Long id);
}
