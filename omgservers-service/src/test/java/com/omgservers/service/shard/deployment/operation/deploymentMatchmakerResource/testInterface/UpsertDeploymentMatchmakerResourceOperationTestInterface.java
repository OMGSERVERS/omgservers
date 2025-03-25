package com.omgservers.service.shard.deployment.operation.deploymentMatchmakerResource.testInterface;

import com.omgservers.schema.model.deploymentMatchmakerResource.DeploymentMatchmakerResourceModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.deployment.impl.operation.deploymentMatchmakerResource.UpsertDeploymentMatchmakerResourceOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertDeploymentMatchmakerResourceOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertDeploymentMatchmakerResourceOperation upsertDeploymentMatchmakerResourceOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final DeploymentMatchmakerResourceModel tenantMatchmakerResource) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertDeploymentMatchmakerResourceOperation
                                    .execute(changeContext,
                                            sqlConnection,
                                            0,
                                            tenantMatchmakerResource))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
