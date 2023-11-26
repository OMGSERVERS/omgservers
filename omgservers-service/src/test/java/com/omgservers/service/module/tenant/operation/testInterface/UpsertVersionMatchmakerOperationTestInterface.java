package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.versionMatchmaker.VersionMatchmakerModel;
import com.omgservers.service.module.tenant.impl.operation.upsertVersionMatchmaker.UpsertVersionMatchmakerOperation;
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
public class UpsertVersionMatchmakerOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertVersionMatchmakerOperation upsertVersionMatchmakerOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersionMatchmaker(final int shard,
                                                          final VersionMatchmakerModel versionMatchmaker) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertVersionMatchmakerOperation
                                    .upsertVersionMatchmaker(changeContext, sqlConnection, shard, versionMatchmaker))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
