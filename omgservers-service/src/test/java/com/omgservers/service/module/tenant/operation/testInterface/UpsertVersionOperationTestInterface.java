package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.version.VersionModel;
import com.omgservers.service.module.tenant.impl.operation.version.upsertVersion.UpsertVersionOperation;
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
public class UpsertVersionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertVersionOperation upsertVersionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersion(final int shard,
                                               final VersionModel version) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertVersionOperation
                                    .upsertVersion(changeContext, sqlConnection, shard, version))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
