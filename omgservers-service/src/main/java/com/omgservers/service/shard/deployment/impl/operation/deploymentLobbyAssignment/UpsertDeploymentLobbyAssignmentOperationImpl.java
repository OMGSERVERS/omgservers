package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyAssignment;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyAssignmentCreatedEventBodyModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertDeploymentLobbyAssignmentOperationImpl implements UpsertDeploymentLobbyAssignmentOperation {

    final ChangeObjectOperation changeObjectOperation;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final DeploymentLobbyAssignmentModel deploymentLobbyAssignment) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_deployment_lobby_assignment(
                            id, idempotency_key, deployment_id, created, modified, client_id, lobby_id, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        deploymentLobbyAssignment.getId(),
                        deploymentLobbyAssignment.getIdempotencyKey(),
                        deploymentLobbyAssignment.getDeploymentId(),
                        deploymentLobbyAssignment.getCreated().atOffset(ZoneOffset.UTC),
                        deploymentLobbyAssignment.getModified().atOffset(ZoneOffset.UTC),
                        deploymentLobbyAssignment.getClientId(),
                        deploymentLobbyAssignment.getLobbyId(),
                        deploymentLobbyAssignment.getDeleted()
                ),
                () -> new DeploymentLobbyAssignmentCreatedEventBodyModel(deploymentLobbyAssignment.getDeploymentId(),
                        deploymentLobbyAssignment.getId()),
                () -> null
        );
    }
}
