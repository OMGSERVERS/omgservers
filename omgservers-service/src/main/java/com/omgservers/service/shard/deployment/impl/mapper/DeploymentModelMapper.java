package com.omgservers.service.shard.deployment.impl.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deployment.DeploymentConfigDto;
import com.omgservers.schema.model.deployment.DeploymentModel;
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
public class DeploymentModelMapper {

    final ObjectMapper objectMapper;

    public DeploymentModel execute(final Row row) {
        final var deployment = new DeploymentModel();
        deployment.setId(row.getLong("id"));
        deployment.setIdempotencyKey(row.getString("idempotency_key"));
        deployment.setCreated(row.getOffsetDateTime("created").toInstant());
        deployment.setModified(row.getOffsetDateTime("modified").toInstant());
        deployment.setTenantId(row.getLong("tenant_id"));
        deployment.setStageId(row.getLong("stage_id"));
        deployment.setVersionId(row.getLong("version_id"));
        deployment.setDeleted(row.getBoolean("deleted"));
        try {
            deployment.setConfig(objectMapper.readValue(row.getString("config"), DeploymentConfigDto.class));
        } catch (IOException e) {
            throw new ServerSideConflictException(ExceptionQualifierEnum.DB_DATA_CORRUPTED,
                    "deployment config can't be parsed, deployment=" + deployment, e);
        }
        return deployment;
    }
}
