package com.omgservers.service.shard.deployment.impl.operation.deployment;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface VerifyDeploymentExistsOperation {
    Uni<Boolean> execute(SqlConnection sqlConnection,
                         int slot,
                         Long id);
}
