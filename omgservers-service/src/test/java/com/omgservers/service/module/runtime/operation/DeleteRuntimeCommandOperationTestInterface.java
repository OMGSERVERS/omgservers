package com.omgservers.service.module.runtime.operation;

import com.omgservers.service.module.runtime.impl.operation.deleteRuntimeCommand.DeleteRuntimeCommandOperation;
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
public class DeleteRuntimeCommandOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteRuntimeCommandOperation deleteRuntimeCommandOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteRuntimeCommand(final int shard,
                                                       final Long runtimeId,
                                                       final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteRuntimeCommandOperation
                                    .deleteRuntimeCommand(changeContext, sqlConnection, shard, runtimeId, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
