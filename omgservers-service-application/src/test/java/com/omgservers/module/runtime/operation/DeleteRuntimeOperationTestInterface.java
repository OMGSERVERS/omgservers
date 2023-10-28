package com.omgservers.module.runtime.operation;

import com.omgservers.module.runtime.impl.operation.deleteRuntime.DeleteRuntimeOperation;
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
public class DeleteRuntimeOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteRuntimeOperation deleteRuntimeOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteRuntime(final int shard,
                                                final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteRuntimeOperation
                                    .deleteRuntime(changeContext, sqlConnection, shard, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}