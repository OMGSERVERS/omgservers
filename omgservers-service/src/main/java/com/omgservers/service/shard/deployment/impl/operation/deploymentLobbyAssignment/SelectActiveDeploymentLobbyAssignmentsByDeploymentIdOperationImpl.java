package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.service.operation.server.SelectListOperation;
import com.omgservers.service.shard.deployment.impl.mapper.DeploymentLobbyAssignmentModelMapper;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectActiveDeploymentLobbyAssignmentsByDeploymentIdOperationImpl
        implements SelectActiveDeploymentLobbyAssignmentsByDeploymentIdOperation {

    final SelectListOperation selectListOperation;

    final DeploymentLobbyAssignmentModelMapper deploymentLobbyAssignmentModelMapper;

    @Override
    public Uni<List<DeploymentLobbyAssignmentModel>> execute(
            final SqlConnection sqlConnection,
            final int shard,
            final Long deploymentId) {
        return selectListOperation.selectList(
                sqlConnection,
                shard,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, client_id, lobby_id, deleted
                        from $schema.tab_deployment_lobby_assignment
                        where deployment_id = $1 and deleted = false
                        order by id asc
                        """,
                List.of(deploymentId),
                "Deployment lobby assignment",
                deploymentLobbyAssignmentModelMapper::execute);
    }
}
