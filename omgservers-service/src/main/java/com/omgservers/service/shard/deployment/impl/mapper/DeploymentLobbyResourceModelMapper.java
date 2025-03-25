package com.omgservers.service.shard.deployment.impl.mapper;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentLobbyResourceModelMapper {

    public DeploymentLobbyResourceModel execute(final Row row) {
        final var deploymentLobbyResource = new DeploymentLobbyResourceModel();
        deploymentLobbyResource.setId(row.getLong("id"));
        deploymentLobbyResource.setIdempotencyKey(row.getString("idempotency_key"));
        deploymentLobbyResource.setDeploymentId(row.getLong("deployment_id"));
        deploymentLobbyResource.setCreated(row.getOffsetDateTime("created").toInstant());
        deploymentLobbyResource.setModified(row.getOffsetDateTime("modified").toInstant());
        deploymentLobbyResource.setLobbyId(row.getLong("lobby_id"));
        deploymentLobbyResource.setStatus(DeploymentLobbyResourceStatusEnum.valueOf(row.getString("status")));
        deploymentLobbyResource.setDeleted(row.getBoolean("deleted"));
        return deploymentLobbyResource;
    }
}
