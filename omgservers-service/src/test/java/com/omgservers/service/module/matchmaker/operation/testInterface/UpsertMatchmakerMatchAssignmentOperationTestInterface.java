package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchAssignment.UpsertMatchmakerMatchAssignmentOperation;
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
public class UpsertMatchmakerMatchAssignmentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerMatchAssignmentOperation operation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final int shard,
                                          final MatchmakerMatchAssignmentModel matchmakerMatchAssignment) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> operation.execute(changeContext,
                                    sqlConnection,
                                    shard,
                                    matchmakerMatchAssignment))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
