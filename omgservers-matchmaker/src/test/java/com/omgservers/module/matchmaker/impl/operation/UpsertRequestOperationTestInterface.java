package com.omgservers.module.matchmaker.impl.operation;

import com.omgservers.model.request.RequestModel;
import com.omgservers.module.matchmaker.impl.operation.upsertRequest.UpsertRequestOperation;
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
public class UpsertRequestOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRequestOperation upsertRequestOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertRequest(final int shard,
                                                final RequestModel request) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRequestOperation
                                    .upsertRequest(changeContext, sqlConnection, shard, request))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
