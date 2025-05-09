package com.omgservers.service.shard.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmaker.MatchmakerModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.UpsertMatchmakerOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertMatchmakerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerOperation upsertMatchmakerOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmaker(final int slot,
                                                   final MatchmakerModel matchmaker) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertMatchmakerOperation
                                    .execute(changeContext, sqlConnection, slot, matchmaker))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
