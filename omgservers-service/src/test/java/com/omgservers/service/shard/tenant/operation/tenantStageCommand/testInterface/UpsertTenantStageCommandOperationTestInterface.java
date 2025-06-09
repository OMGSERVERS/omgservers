package com.omgservers.service.shard.tenant.operation.tenantStageCommand.testInterface;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.service.shard.tenant.impl.operation.tenantStageCommand.UpsertTenantStageCommandOperation;
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
public class UpsertTenantStageCommandOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTenantStageCommandOperation upsertTenantStageCommandOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final TenantStageCommandModel tenantStageCommand) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantStageCommandOperation
                                    .execute(changeContext, sqlConnection, 0, tenantStageCommand))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}