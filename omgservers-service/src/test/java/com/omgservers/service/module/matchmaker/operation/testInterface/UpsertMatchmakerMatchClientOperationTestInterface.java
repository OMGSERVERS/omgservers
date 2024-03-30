package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchClient.upsertMatchmakerMatchClient.UpsertMatchmakerMatchClientOperation;
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
public class UpsertMatchmakerMatchClientOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerMatchClientOperation upsertMatchmakerMatchClientOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmakerMatchClient(final int shard,
                                                              final MatchmakerMatchClientModel matchmakerMatchClient) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertMatchmakerMatchClientOperation
                                    .upsertMatchmakerMatchClient(changeContext, sqlConnection, shard, matchmakerMatchClient))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
