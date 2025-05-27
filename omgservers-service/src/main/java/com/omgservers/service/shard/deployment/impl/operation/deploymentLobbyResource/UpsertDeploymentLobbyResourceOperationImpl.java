package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyResourceCreatedEventBodyModel;
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
class UpsertDeploymentLobbyResourceOperationImpl implements UpsertDeploymentLobbyResourceOperation {

    final ChangeObjectOperation changeObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int slot,
                                final DeploymentLobbyResourceModel deploymentLobbyResource) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, slot,
                """
                        insert into $slot.tab_deployment_lobby_resource(
                            id, idempotency_key, deployment_id, created, modified, lobby_id, status, config, deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8, $9)
                        on conflict (id) do
                        nothing
                        """,
                List.of(
                        deploymentLobbyResource.getId(),
                        deploymentLobbyResource.getIdempotencyKey(),
                        deploymentLobbyResource.getDeploymentId(),
                        deploymentLobbyResource.getCreated().atOffset(ZoneOffset.UTC),
                        deploymentLobbyResource.getModified().atOffset(ZoneOffset.UTC),
                        deploymentLobbyResource.getLobbyId(),
                        deploymentLobbyResource.getStatus(),
                        getConfigString(deploymentLobbyResource),
                        deploymentLobbyResource.getDeleted()
                ),
                () -> new DeploymentLobbyResourceCreatedEventBodyModel(deploymentLobbyResource.getDeploymentId(),
                        deploymentLobbyResource.getId()),
                () -> null
        );
    }

    String getConfigString(final DeploymentLobbyResourceModel deploymentLobbyResource) {
        try {
            return objectMapper.writeValueAsString(deploymentLobbyResource.getConfig());
        } catch (IOException e) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.WRONG_OBJECT, e.getMessage(), e);
        }
    }
}
