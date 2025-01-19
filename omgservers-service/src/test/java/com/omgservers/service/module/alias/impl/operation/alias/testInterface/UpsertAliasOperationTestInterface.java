package com.omgservers.service.module.alias.impl.operation.alias.testInterface;

import com.omgservers.schema.model.alias.AliasModel;
import com.omgservers.service.module.alias.impl.operation.alias.UpsertAliasOperation;
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
public class UpsertAliasOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertAliasOperation upsertAliasOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> execute(final int shard,
                                          final AliasModel alias) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertAliasOperation
                                    .execute(changeContext, sqlConnection, shard, alias))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
