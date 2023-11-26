package com.omgservers.service.module.runtime.operation.testInterface;

import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
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
public class UpsertRuntimeOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRuntimeOperation upsertRuntimeOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertRuntime(final int shard,
                                                final RuntimeModel runtime) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRuntimeOperation
                                    .upsertRuntime(changeContext, sqlConnection, shard, runtime))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
