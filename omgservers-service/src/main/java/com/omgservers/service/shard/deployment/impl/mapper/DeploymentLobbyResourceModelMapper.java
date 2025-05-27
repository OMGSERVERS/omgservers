package com.omgservers.service.shard.deployment.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceConfigDto;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceStatusEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceConfigDto;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentLobbyResourceModelMapper {

    final ObjectMapper objectMapper;

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
        try {
            deploymentLobbyResource.setConfig(objectMapper.readValue(row.getString("config"),
                    DeploymentLobbyResourceConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "deployment lobby resource config can't be parsed, deploymentLobbyResource=" +
                            deploymentLobbyResource, e);
        }
        return deploymentLobbyResource;
    }
}
