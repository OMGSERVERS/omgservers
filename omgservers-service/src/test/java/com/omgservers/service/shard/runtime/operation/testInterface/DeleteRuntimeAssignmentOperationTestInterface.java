package com.omgservers.service.shard.runtime.operation.testInterface;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.DeleteRuntimeAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeleteRuntimeAssignmentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteRuntimeAssignmentOperation deleteRuntimeAssignmentOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteRuntimeAssignment(final int slot,
                                                          final Long runtimeId,
                                                          final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteRuntimeAssignmentOperation
                                    .execute(changeContext, sqlConnection, slot, runtimeId, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
