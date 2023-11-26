package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.service.module.matchmaker.impl.operation.upsertMatchmaker.UpsertMatchmakerOperation;
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
public class UpsertMatchmakerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerOperation upsertMatchmakerOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmaker(final int shard,
                                                   final MatchmakerModel matchmaker) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertMatchmakerOperation
                                    .upsertMatchmaker(changeContext, sqlConnection, shard, matchmaker))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
