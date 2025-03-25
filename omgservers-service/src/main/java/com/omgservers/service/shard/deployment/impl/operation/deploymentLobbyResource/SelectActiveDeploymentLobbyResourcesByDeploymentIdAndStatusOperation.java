package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveDeploymentLobbyResourcesByDeploymentIdAndStatusOperation {
    Uni<List<DeploymentLobbyResourceModel>> execute(SqlConnection sqlConnection,
                                                    int shard,
                                                    Long deploymentId,
                                                    DeploymentLobbyResourceStatusEnum status);
}
