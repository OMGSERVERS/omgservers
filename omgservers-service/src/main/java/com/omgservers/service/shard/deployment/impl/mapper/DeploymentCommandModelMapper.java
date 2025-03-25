package com.omgservers.service.shard.deployment.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import com.omgservers.schema.model.deploymentCommand.DeploymentCommandQualifierEnum;
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
public class DeploymentCommandModelMapper {

    final ObjectMapper objectMapper;

    public DeploymentCommandModel execute(final Row row) {
        final var deploymentCommand = new DeploymentCommandModel();
        deploymentCommand.setId(row.getLong("id"));
        deploymentCommand.setIdempotencyKey(row.getString("idempotency_key"));
        deploymentCommand.setDeploymentId(row.getLong("deployment_id"));
        deploymentCommand.setCreated(row.getOffsetDateTime("created").toInstant());
        deploymentCommand.setModified(row.getOffsetDateTime("modified").toInstant());
        final var qualifier = DeploymentCommandQualifierEnum
                .valueOf(row.getString("qualifier"));
        deploymentCommand.setQualifier(qualifier);
        deploymentCommand.setDeleted(row.getBoolean("deleted"));
        try {
            final var body = objectMapper
                    .readValue(row.getString("body"), qualifier.getBodyClass());
            deploymentCommand.setBody(body);
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "deployment command can't be parsed, deploymentCommand=" + deploymentCommand, e);
        }
        return deploymentCommand;
    }
}
