package com.omgservers.service.shard.matchmaker.operation.testInterface;

import com.omgservers.service.shard.matchmaker.impl.operation.matchmaker.DeleteMatchmakerOperation;
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
public class DeleteMatchmakerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteMatchmakerOperation deleteMatchmakerOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteMatchmaker(final int slot,
                                                   final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteMatchmakerOperation
                                    .execute(changeContext, sqlConnection, slot, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
