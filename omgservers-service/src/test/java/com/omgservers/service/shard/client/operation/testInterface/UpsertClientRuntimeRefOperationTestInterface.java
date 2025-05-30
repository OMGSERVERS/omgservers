package com.omgservers.service.shard.client.operation.testInterface;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.shard.client.impl.operation.clientRuntimeRef.UpsertClientRuntimeRefOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UpsertClientRuntimeRefOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertClientRuntimeRefOperation upsertClientRuntimeRefOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertClientRuntimeRef(final int slot,
                                                         final ClientRuntimeRefModel clientRuntimeRef) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertClientRuntimeRefOperation
                                    .upsertClientRuntimeRef(changeContext, sqlConnection, slot, clientRuntimeRef))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
