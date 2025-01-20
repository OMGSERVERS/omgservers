package com.omgservers.service.shard.user.operation.testInterface;

import com.omgservers.schema.model.user.UserModel;
import com.omgservers.service.shard.user.impl.operation.user.selectUser.SelectUserOperation;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SelectUserOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final SelectUserOperation selectUserOperation;

    final PgPool pgPool;

    public UserModel selectUser(final int shard,
                                final Long id) {
        return pgPool.withTransaction(sqlConnection -> selectUserOperation
                        .selectUser(sqlConnection, shard, id))
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
