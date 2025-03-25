package com.omgservers.service.shard.matchmaker.operation.testInterface;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatchAssignment.DeleteMatchmakerMatchAssignmentOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeleteMatchmakerMatchAssignmentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteMatchmakerMatchAssignmentOperation operation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final Long matchmakerId,
                                          final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> operation.execute(changeContext,
                                    sqlConnection,
                                    0,
                                    matchmakerId,
                                    id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
