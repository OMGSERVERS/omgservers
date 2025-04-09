package com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.service.event.body.module.deployment.DeploymentLobbyResourceCreatedEventBodyModel;
import com.omgservers.service.factory.system.LogModelFactory;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertDeploymentLobbyResourceOperationImpl implements UpsertDeploymentLobbyResourceOperation {

    final ChangeObjectOperation changeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> execute(final ChangeContext<?> changeContext,
                                final SqlConnection sqlConnection,
                                final int shard,
                                final DeploymentLobbyResourceModel deploymentLobbyResource) {
        return changeObjectOperation.execute(
                changeContext, sqlConnection, shard,
                """
                        insert into $shard.tab_deployment_lobby_resource(
                            id, idempotency_key, deployment_id, created, modified, lobby_id, status,
                            deleted)
                        values($1, $2, $3, $4, $5, $6, $7, $8)
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
                        deploymentLobbyResource.getDeleted()
                ),
                () -> new DeploymentLobbyResourceCreatedEventBodyModel(deploymentLobbyResource.getDeploymentId(),
                        deploymentLobbyResource.getId()),
                () -> null
        );
    }
}
