package com.omgservers.module.matchmaker.impl.operation;

import com.omgservers.module.matchmaker.impl.operation.deleteRequest.DeleteRequestOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DeleteRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteRequestOperation deleteRequestOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteRequest(final int shard,
                                                final Long matchmakerId,
                                                final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteRequestOperation
                                    .deleteRequest(changeContext, sqlConnection, shard, matchmakerId, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .invoke(changeContext -> log.info("Change context, {}", changeContext))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
