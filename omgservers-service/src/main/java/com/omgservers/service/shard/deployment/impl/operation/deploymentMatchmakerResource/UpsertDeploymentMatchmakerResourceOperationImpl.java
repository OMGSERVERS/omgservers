package com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentMatchmakerResourceCreatedEventBodyModel;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertDeploymentMatchmakerResourceOperationImpl implements UpsertDeploymentMatchmakerResourceOperation {

    final ChangeObjectOperation changeObjectOperation;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final DeploymentMatchmakerResourceModel deploymentMatchmakerResource) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_deployment_matchmaker_resource(
                            id, idempotency_key, deployment_id, created, modified, matchmaker_id, status, config, 
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        deploymentMatchmakerResource.getId(),
                        deploymentMatchmakerResource.getIdempotencyKey(),
                        deploymentMatchmakerResource.getDeploymentId(),
                        deploymentMatchmakerResource.getCreated().atOffset(ZoneOffset.UTC),
                        deploymentMatchmakerResource.getModified().atOffset(ZoneOffset.UTC),
                        deploymentMatchmakerResource.getMatchmakerId(),
                        deploymentMatchmakerResource.getStatus(),
                        getConfigString(deploymentMatchmakerResource),
                        deploymentMatchmakerResource.getDeleted()
                ),
                () -> new DeploymentMatchmakerResourceCreatedEventBodyModel(
                        deploymentMatchmakerResource.getDeploymentId(),
                        deploymentMatchmakerResource.getId()),
                () -> null
        );
    }

    String getConfigString(final DeploymentMatchmakerResourceModel deploymentMatchmakerResource) {
        try {
            return objectMapper.writeValueAsString(deploymentMatchmakerResource.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
