package com.omgservers.service.shard.runtime.operation.testInterface;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.runtime.impl.operation.runtime.DeleteRuntimeOperation;
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

    public ChangeContext<Boolean> deleteRuntime(final int slot,
                                                final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteRuntimeOperation
                                    .execute(changeContext, sqlConnection, slot, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
