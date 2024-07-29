package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.schema.model.versionMatchmakerRef.VersionMatchmakerRefModel;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRef.upsertVersionMatchmakerRef.UpsertVersionMatchmakerRefOperation;
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
public class UpsertVersionMatchmakerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertVersionMatchmakerRefOperation upsertVersionMatchmakerRefOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersionMatchmaker(final int shard,
                                                          final VersionMatchmakerRefModel versionMatchmaker) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertVersionMatchmakerRefOperation
                                    .upsertVersionMatchmakerRef(changeContext, sqlConnection, shard, versionMatchmaker))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
