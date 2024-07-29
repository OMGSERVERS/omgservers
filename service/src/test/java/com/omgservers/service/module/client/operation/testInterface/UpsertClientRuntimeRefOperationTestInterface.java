package com.omgservers.service.module.client.operation.testInterface;

import com.omgservers.schema.model.clientRuntimeRef.ClientRuntimeRefModel;
import com.omgservers.service.module.client.impl.operation.clientRuntimeRef.upsertClientRuntimeRef.UpsertClientRuntimeRefOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
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

    public ChangeContext<Boolean> upsertClientRuntimeRef(final int shard,
                                                         final ClientRuntimeRefModel clientRuntimeRef) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertClientRuntimeRefOperation
                                    .upsertClientRuntimeRef(changeContext, sqlConnection, shard, clientRuntimeRef))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
