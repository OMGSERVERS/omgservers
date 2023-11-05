package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.stage.StageModel;
import com.omgservers.service.module.tenant.impl.operation.selectStage.SelectStageOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectStageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectStageOperation selectStageOperation;

    final PgPool pgPool;

    public StageModel selectStage(final int shard,
                                  final Long tenantId,
                                  final Long id,
                                  final Boolean deleted) {
        return pgPool.withTransaction(sqlConnection -> selectStageOperation
                        .selectStage(sqlConnection, shard, tenantId, id, deleted))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
