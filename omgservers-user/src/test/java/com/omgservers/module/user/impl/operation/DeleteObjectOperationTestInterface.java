package com.omgservers.module.user.impl.operation;

import com.omgservers.module.user.impl.operation.deleteObject.DeleteObjectOperation;
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
public class DeleteObjectOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteObjectOperation deleteObjectOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteObject(final int shard,
                                               final Long userId,
                                               final Long playerId,
                                               final String name) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteObjectOperation
                                    .deleteObject(changeContext, sqlConnection, shard, userId, playerId, name))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .invoke(changeContext -> log.info("Change context, {}", changeContext))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
