package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmakerMatchRuntimeRef.MatchmakerMatchRuntimeRefModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerMatchRuntimeRef.upsertMatchmakerMatchRuntimeRef.UpsertMatchmakerMatchRuntimeRefOperation;
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
public class UpsertMatchmakerMatchRuntimeRefOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerMatchRuntimeRefOperation upsertMatchmakerMatchRuntimeRefOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmakerMatchRuntimeRef(final int shard,
                                                                  final MatchmakerMatchRuntimeRefModel matchmakerMatchRuntimeRefModel) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertMatchmakerMatchRuntimeRefOperation
                                    .upsertMatchmakerMatchRuntimeRef(changeContext, sqlConnection, shard, matchmakerMatchRuntimeRefModel))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
