package com.omgservers.service.module.user.operation.testInterface;

import com.omgservers.model.token.TokenModel;
import com.omgservers.service.module.user.impl.operation.upsertToken.UpsertTokenOperation;
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
public class UpsertTokenOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertTokenOperation upsertTokenOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertToken(final int shard,
                                              final TokenModel token) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTokenOperation
                                    .upsertToken(changeContext, sqlConnection, shard, token))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
