package com.omgservers.service.module.client.operation.testInterface;

import com.omgservers.schema.model.clientMessage.ClientMessageModel;
import com.omgservers.service.module.client.impl.operation.clientMessage.upsertClientMessage.UpsertClientMessageOperation;
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
public class UpsertClientMessageOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertClientMessageOperation upsertClientMessageOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertClientMessage(final int shard,
                                                      final ClientMessageModel clientMessage) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertClientMessageOperation
                                    .upsertClientMessage(changeContext, sqlConnection, shard, clientMessage))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
