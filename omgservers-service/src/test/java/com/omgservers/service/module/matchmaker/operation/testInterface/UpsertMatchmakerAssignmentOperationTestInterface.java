package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmakerAssignment.MatchmakerAssignmentModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerAssignment.UpsertMatchmakerAssignmentOperation;
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
public class UpsertMatchmakerAssignmentOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerAssignmentOperation upsertMatchmakerAssignmentOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmakerAssignment(final int shard,
                                                             final MatchmakerAssignmentModel matchmakerAssignment) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertMatchmakerAssignmentOperation
                                    .execute(changeContext, sqlConnection, shard, matchmakerAssignment))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
