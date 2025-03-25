package com.omgservers.service.shard.deployment.impl.operation.deployment;

import com.omgservers.schema.model.deployment.DeploymentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectDeploymentOperation {
    Uni<DeploymentModel> execute(SqlConnection sqlConnection,
                                 int shard,
                                 Long id);
}
