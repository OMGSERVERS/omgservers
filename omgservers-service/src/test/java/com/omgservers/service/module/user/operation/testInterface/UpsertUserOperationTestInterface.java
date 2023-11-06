package com.omgservers.service.module.user.operation.testInterface;

import com.omgservers.model.user.UserModel;
import com.omgservers.service.module.user.impl.operation.upsertUser.UpsertUserOperation;
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
public class UpsertUserOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertUserOperation upsertUserOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertUser(final int shard,
                                             final UserModel user) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertUserOperation
                                    .upsertUser(changeContext, sqlConnection, shard, user))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
