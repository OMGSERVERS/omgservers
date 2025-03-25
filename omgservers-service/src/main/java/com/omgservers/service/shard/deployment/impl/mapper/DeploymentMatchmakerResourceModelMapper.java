package com.omgservers.service.shard.deployment.impl.mapper;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceStatusEnum;
import io.vertx.mutiny.sqlclient.Row;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeploymentMatchmakerResourceModelMapper {

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
        return deploymentMatchmakerResource;
    }
}
