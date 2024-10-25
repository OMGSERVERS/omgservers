package com.omgservers.service.module.matchmaker.operation.testInterface;

import com.omgservers.schema.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.service.module.matchmaker.impl.operation.matchmakerCommand.UpsertMatchmakerCommandOperation;
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
public class UpsertMatchmakerCommandOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertMatchmakerCommandOperation upsertMatchmakerCommandOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertMatchmakerCommand(final int shard,
                                                          final MatchmakerCommandModel matchmakerCommand) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertMatchmakerCommandOperation
                                    .execute(changeContext, sqlConnection, shard, matchmakerCommand))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
