package com.omgservers.service.module.runtime.operation.testInterface;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.service.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
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
public class UpsertRuntimeCommandOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertRuntimeCommand(final int shard,
                                                       final RuntimeCommandModel runtimeCommand) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRuntimeCommandOperation
                                    .upsertRuntimeCommand(changeContext, sqlConnection, shard, runtimeCommand))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
