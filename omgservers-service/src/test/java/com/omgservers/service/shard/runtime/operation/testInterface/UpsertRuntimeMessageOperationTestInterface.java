package com.omgservers.service.shard.runtime.operation.testInterface;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.runtime.impl.operation.runtimeMessage.UpsertRuntimeMessageOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertRuntimeMessageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRuntimeMessageOperation operation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final RuntimeMessageModel runtimeMessage) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> operation
                                    .execute(changeContext, sqlConnection, 0, runtimeMessage))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
