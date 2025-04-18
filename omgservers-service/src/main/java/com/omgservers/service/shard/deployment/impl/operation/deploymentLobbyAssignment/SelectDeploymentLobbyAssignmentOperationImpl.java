package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.service.operation.server.SelectObjectOperation;
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
class SelectDeploymentLobbyAssignmentOperationImpl implements SelectDeploymentLobbyAssignmentOperation {

    final SelectObjectOperation selectObjectOperation;

    final DeploymentLobbyAssignmentModelMapper deploymentLobbyAssignmentModelMapper;

    @Override
    public Uni<DeploymentLobbyAssignmentModel> execute(final SqlConnection sqlConnection,
                                                       final int slot,
                                                       final Long deploymentId,
                                                       final Long id) {
        return selectObjectOperation.selectObject(
                sqlConnection,
                slot,
                """
                        select
                            id, idempotency_key, deployment_id, created, modified, client_id, lobby_id, deleted
                        from $slot.tab_deployment_lobby_assignment
                        where deployment_id = $1 and id = $2
                        limit 1
                        """,
                List.of(deploymentId, id),
                "Deployment lobby assignment",
                deploymentLobbyAssignmentModelMapper::execute);
    }
}
