package com.omgservers.service.shard.deployment.impl.operation.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectDeploymentRequestByClientIdOperation {
    Uni<DeploymentRequestModel> execute(SqlConnection sqlConnection,
                                        int shard,
                                        Long deploymentId,
                                        Long clientId);
}
