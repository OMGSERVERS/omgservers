package com.omgservers.service.shard.deployment.impl.mapper;

import com.omgservers.schema.model.deploymentLobbyAssignment.DeploymentLobbyAssignmentModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentLobbyAssignmentModelMapper {

    public DeploymentLobbyAssignmentModel execute(final Row row) {
        final var deploymentLobbyAssignment = new DeploymentLobbyAssignmentModel();
        deploymentLobbyAssignment.setId(row.getLong("id"));
        deploymentLobbyAssignment.setIdempotencyKey(row.getString("idempotency_key"));
        deploymentLobbyAssignment.setDeploymentId(row.getLong("deployment_id"));
        deploymentLobbyAssignment.setCreated(row.getOffsetDateTime("created").toInstant());
        deploymentLobbyAssignment.setModified(row.getOffsetDateTime("modified").toInstant());
        deploymentLobbyAssignment.setClientId(row.getLong("client_id"));
        deploymentLobbyAssignment.setLobbyId(row.getLong("lobby_id"));
        deploymentLobbyAssignment.setDeleted(row.getBoolean("deleted"));
        return deploymentLobbyAssignment;
    }
}
