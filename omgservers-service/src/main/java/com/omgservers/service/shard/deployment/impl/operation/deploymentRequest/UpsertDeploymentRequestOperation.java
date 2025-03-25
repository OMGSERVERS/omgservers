package com.omgservers.service.shard.deployment.impl.operation.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface UpsertDeploymentRequestOperation {
    Uni<Boolean> execute(ChangeContext<?> changeContext,
                         SqlConnection sqlConnection,
                         int shard,
                         DeploymentRequestModel deploymentRequest);
}
