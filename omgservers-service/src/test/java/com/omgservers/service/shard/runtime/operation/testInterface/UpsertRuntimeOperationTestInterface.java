package com.omgservers.service.shard.runtime.operation.testInterface;

import com.omgservers.schema.model.runtime.RuntimeModel;
import com.omgservers.service.shard.runtime.impl.operation.runtime.UpsertRuntimeOperation;
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
public class UpsertRuntimeOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRuntimeOperation upsertRuntimeOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertRuntime(final int slot,
                                                final RuntimeModel runtime) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRuntimeOperation
                                    .execute(changeContext, sqlConnection, slot, runtime))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
