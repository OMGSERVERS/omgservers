package com.omgservers.service.shard.deployment.impl.mapper;

import com.omgservers.schema.model.deploymentMatchmakerAssignment.DeploymentMatchmakerAssignmentModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentMatchmakerAssignmentModelMapper {

    public DeploymentMatchmakerAssignmentModel execute(final Row row) {
        final var deploymentMatchmakerAssignment = new DeploymentMatchmakerAssignmentModel();
        deploymentMatchmakerAssignment.setId(row.getLong("id"));
        deploymentMatchmakerAssignment.setIdempotencyKey(row.getString("idempotency_key"));
        deploymentMatchmakerAssignment.setDeploymentId(row.getLong("deployment_id"));
        deploymentMatchmakerAssignment.setCreated(row.getOffsetDateTime("created").toInstant());
        deploymentMatchmakerAssignment.setModified(row.getOffsetDateTime("modified").toInstant());
        deploymentMatchmakerAssignment.setClientId(row.getLong("client_id"));
        deploymentMatchmakerAssignment.setMatchmakerId(row.getLong("matchmaker_id"));
        deploymentMatchmakerAssignment.setDeleted(row.getBoolean("deleted"));
        return deploymentMatchmakerAssignment;
    }
}
