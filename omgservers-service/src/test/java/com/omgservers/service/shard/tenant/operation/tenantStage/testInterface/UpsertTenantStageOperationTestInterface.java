package com.omgservers.service.shard.tenant.operation.tenantStage.testInterface;

import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantStage.UpsertTenantStageOperation;
import com.omgservers.service.operation.server.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertTenantStageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantStageOperation upsertTenantStageOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantStageModel tenantStage) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantStageOperation
                                    .execute(changeContext, sqlConnection, 0, tenantStage))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
