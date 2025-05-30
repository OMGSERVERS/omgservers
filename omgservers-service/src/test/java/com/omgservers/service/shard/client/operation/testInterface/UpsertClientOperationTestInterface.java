package com.omgservers.service.shard.client.operation.testInterface;

import com.omgservers.schema.model.client.ClientModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.client.impl.operation.client.UpsertClientOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertClientOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertClientOperation upsertClientOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertClient(final int slot,
                                               final ClientModel client) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertClientOperation
                                    .upsertClient(changeContext, sqlConnection, slot, client))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
