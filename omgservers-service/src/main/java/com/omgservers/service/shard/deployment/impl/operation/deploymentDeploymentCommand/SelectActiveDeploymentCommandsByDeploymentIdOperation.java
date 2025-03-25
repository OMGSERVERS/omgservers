package com.omgservers.service.shard.deployment.impl.operation.deploymentDeploymentCommand;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveDeploymentCommandsByDeploymentIdOperation {
    Uni<List<DeploymentCommandModel>> execute(SqlConnection sqlConnection,
                                              int shard,
                                              Long deploymentId);
}
