package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.project.ProjectModel;
import com.omgservers.service.module.tenant.impl.operation.project.selectProject.SelectProjectOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectProjectOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectProjectOperation selectProjectOperation;

    final PgPool pgPool;

    public ProjectModel selectProject(final int shard,
                                      final Long tenantId,
                                      final Long id,
                                      final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectProjectOperation
                        .selectProject(sqlConnection, shard, tenantId, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
