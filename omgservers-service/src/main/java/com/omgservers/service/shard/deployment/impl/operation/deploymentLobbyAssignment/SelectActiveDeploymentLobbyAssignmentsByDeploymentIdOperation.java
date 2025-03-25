package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;

import java.util.List;

public interface SelectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation {
    Uni<List<DeploymentLobbyAssignmentModel>> execute(SqlConnection sqlConnection,
                                                      int shard,
                                                      Long deploymentId);
}
