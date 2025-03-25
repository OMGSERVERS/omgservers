package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

public interface SelectDeploymentLobbyResourceByLobbyIdOperation {
    Uni<DeploymentLobbyResourceModel> execute(SqlConnection sqlConnection,
                                              int shard,
                                              Long deploymentId,
                                              Long lobbyId);
}
