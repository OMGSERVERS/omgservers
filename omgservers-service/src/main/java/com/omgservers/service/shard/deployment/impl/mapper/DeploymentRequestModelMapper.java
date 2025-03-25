package com.omgservers.service.shard.deployment.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentRequest.DeploymentRequestModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentRequestModelMapper {

    final ObjectMapper objectMapper;

    public DeploymentRequestModel execute(final Row row) {
        final var deploymentRequest = new DeploymentRequestModel();
        deploymentRequest.setId(row.getLong("id"));
        deploymentRequest.setIdempotencyKey(row.getString("idempotency_key"));
        deploymentRequest.setDeploymentId(row.getLong("deployment_id"));
        deploymentRequest.setCreated(row.getOffsetDateTime("created").toInstant());
        deploymentRequest.setModified(row.getOffsetDateTime("modified").toInstant());
        deploymentRequest.setClientId(row.getLong("client_id"));
        deploymentRequest.setDeleted(row.getBoolean("deleted"));
        return deploymentRequest;
    }
}
