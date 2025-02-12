package com.omgservers.service.shard.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmakerMatch.UpsertMatchmakerMatchOperation;
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
public class UpsertMatchmakerMatchOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerMatchOperation upsertMatchmakerMatchOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmakerMatch(final int shard,
                                                        final MatchmakerMatchModel matchmakerMatch) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertMatchmakerMatchOperation
                                    .execute(changeContext, sqlConnection, shard, matchmakerMatch))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
