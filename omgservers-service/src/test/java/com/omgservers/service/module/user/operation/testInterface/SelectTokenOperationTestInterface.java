package com.omgservers.service.module.user.operation.testInterface;

import com.omgservers.model.token.TokenModel;
import com.omgservers.service.module.user.impl.operation.selectToken.SelectTokenOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectTokenOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectTokenOperation selectTokenOperation;

    final PgPool pgPool;

    public TokenModel selectToken(final int shard, final Long tokenId) {
        return pgPool.withTransaction(sqlConnection -> selectTokenOperation
                        .selectToken(sqlConnection, shard, tokenId))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
