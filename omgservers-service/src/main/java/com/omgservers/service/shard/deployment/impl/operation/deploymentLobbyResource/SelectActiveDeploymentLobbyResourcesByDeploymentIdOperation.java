package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveDeploymentLobbyResourcesByDeploymentIdOperation {
    Uni<List<DeploymentLobbyResourceModel>> execute(SqlConnection sqlConnection,
                                                    int slot,
                                                    Long deploymentId);
}
