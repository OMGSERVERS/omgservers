package com.omgservers.service.shard.deployment.impl.operation.deploymentRequest;

import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveDeploymentRequestsByDeploymentIdOperation {
    Uni<List<DeploymentRequestModel>> execute(SqlConnection sqlConnection,
                                              int shard,
                                              Long deploymentId);
}
