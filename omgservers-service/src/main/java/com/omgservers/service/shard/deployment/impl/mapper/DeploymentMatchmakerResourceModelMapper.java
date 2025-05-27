package com.omgservers.service.shard.deployment.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceConfigDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceConfigDto;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentMatchmakerResourceModelMapper {

    final ObjectMapper objectMapper;

    public DeploymentMatchmakerResourceModel execute(final Row row) {
        final var deploymentMatchmakerResource = new DeploymentMatchmakerResourceModel();
        deploymentMatchmakerResource.setId(row.getLong("id"));
        deploymentMatchmakerResource.setIdempotencyKey(row.getString("idempotency_key"));
        deploymentMatchmakerResource.setDeploymentId(row.getLong("deployment_id"));
        deploymentMatchmakerResource.setCreated(row.getOffsetDateTime("created").toInstant());
        deploymentMatchmakerResource.setModified(row.getOffsetDateTime("modified").toInstant());
        deploymentMatchmakerResource.setMatchmakerId(row.getLong("matchmaker_id"));
        deploymentMatchmakerResource.setStatus(DeploymentMatchmakerResourceStatusEnum.valueOf(row.getString("status")));
        deploymentMatchmakerResource.setDeleted(row.getBoolean("deleted"));
        try {
            deploymentMatchmakerResource.setConfig(objectMapper.readValue(row.getString("config"),
                    DeploymentMatchmakerResourceConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "deployment matchmaker resource config can't be parsed, deploymentMatchmakerResource=" +
                            deploymentMatchmakerResource, e);
        }
        return deploymentMatchmakerResource;
    }
}
