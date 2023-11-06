package com.omgservers.service.module.runtime.operation.testInterface;

import com.omgservers.model.runtimeGrant.RuntimeGrantModel;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntimeGrant.UpsertRuntimeGrantOperation;
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
public class UpsertRuntimeGrantOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRuntimeGrantOperation upsertRuntimeGrantOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertRuntimeGrant(final int shard,
                                                     final RuntimeGrantModel runtimeGrant) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRuntimeGrantOperation
                                    .upsertRuntimeGrant(changeContext, sqlConnection, shard, runtimeGrant))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
