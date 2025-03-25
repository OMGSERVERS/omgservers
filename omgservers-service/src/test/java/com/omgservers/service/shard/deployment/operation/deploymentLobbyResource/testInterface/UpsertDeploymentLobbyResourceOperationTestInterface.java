package com.omgservers.service.shard.deployment.operation.deploymentLobbyResource.testInterface;

import com.omgservers.schema.model.deploymentLobbyResource.DeploymentLobbyResourceModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.deployment.impl.operation.deploymentLobbyResource.UpsertDeploymentLobbyResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertDeploymentLobbyResourceOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertDeploymentLobbyResourceOperation upsertDeploymentLobbyResourceOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final DeploymentLobbyResourceModel tenantLobbyResource) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertDeploymentLobbyResourceOperation
                                    .execute(changeContext,
                                            sqlConnection,
                                            0,
                                            tenantLobbyResource))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
