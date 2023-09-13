package com.omgservers.module.tenant.impl.operation;

import com.omgservers.module.tenant.impl.operation.deleteVersion.DeleteVersionOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeleteVersionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteVersionOperation deleteVersionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteVersion(final int shard,
                                                final Long tenantId,
                                                final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteVersionOperation
                                    .deleteVersion(changeContext, sqlConnection, shard, tenantId, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .invoke(changeContext -> log.info("Change context, {}", changeContext))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
