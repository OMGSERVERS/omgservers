package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.UpsertTenantStageOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertStageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantStageOperation upsertTenantStageOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertStage(final int shard,
                                              final TenantStageModel stage) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantStageOperation
                                    .execute(changeContext, sqlConnection, shard, stage))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
