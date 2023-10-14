package com.omgservers.module.user.impl.operation;

import com.omgservers.module.user.impl.operation.deleteClient.DeleteClientOperation;
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
public class DeleteClientOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final DeleteClientOperation deleteClientOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> deleteClient(final int shard,
                                               final Long userId,
                                               final Long id) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> deleteClientOperation
                                    .deleteClient(changeContext, sqlConnection, shard, userId, id))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
