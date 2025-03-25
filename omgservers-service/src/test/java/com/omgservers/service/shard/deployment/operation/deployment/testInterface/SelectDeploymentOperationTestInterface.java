package com.omgservers.service.shard.deployment.operation.deployment.testInterface;

import com.omgservers.schema.model.deployment.DeploymentModel;
import com.omgservers.service.shard.deployment.impl.operation.deployment.SelectDeploymentOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectDeploymentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectDeploymentOperation selectDeploymentOperation;

    final PgPool pgPool;

    public DeploymentModel execute(final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectDeploymentOperation
                        .execute(sqlConnection, 0, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
