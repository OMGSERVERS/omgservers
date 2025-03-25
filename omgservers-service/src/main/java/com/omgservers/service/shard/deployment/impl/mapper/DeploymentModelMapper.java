package com.omgservers.service.shard.deployment.impl.mapper;

import com.omgservers.schema.model.deployment.DeploymentModel;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentModelMapper {

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
        return deployment;
    }
}
