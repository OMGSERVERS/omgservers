package com.omgservers.service.module.tenant.impl.operation.testInterface;

import com.omgservers.schema.model.tenantMatchmakerRef.TenantMatchmakerRefModel;
import com.omgservers.service.module.tenant.impl.operation.tenantMatchmakerRef.UpsertTenantMatchmakerRefOperation;
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

    final UpsertTenantMatchmakerRefOperation upsertTenantMatchmakerRefOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertVersionMatchmaker(final int shard,
                                                          final TenantMatchmakerRefModel versionMatchmaker) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertTenantMatchmakerRefOperation
                                    .execute(changeContext, sqlConnection, shard, versionMatchmaker))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
