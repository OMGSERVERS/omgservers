package com.omgservers.service.shard.deployment.impl.operation.deployment;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertDeploymentOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int slot,
                         DeploymentModel deployment);
}
