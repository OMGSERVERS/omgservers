package com.omgservers.service.shard.runtime.operation.testInterface;

import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.service.shard.runtime.impl.operation.runtimeAssignment.UpsertRuntimeAssignmentOperation;
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
public class UpsertRuntimeAssignmentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRuntimeAssignmentOperation upsertRuntimeAssignmentOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertRuntimeAssignment(final int slot,
                                                          final RuntimeAssignmentModel runtimeAssignment) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRuntimeAssignmentOperation
                                    .execute(changeContext, sqlConnection, slot, runtimeAssignment))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
