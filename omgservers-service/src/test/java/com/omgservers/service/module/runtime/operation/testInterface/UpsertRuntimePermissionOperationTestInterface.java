package com.omgservers.service.module.runtime.operation.testInterface;

import com.omgservers.schema.model.runtimePermission.RuntimePermissionModel;
import com.omgservers.service.module.runtime.impl.operation.runtimePermission.UpsertRuntimePermissionOperation;
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
public class UpsertRuntimePermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertRuntimePermissionOperation upsertRuntimePermissionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertRuntimePermission(final int shard,
                                                          final RuntimePermissionModel runtimePermission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertRuntimePermissionOperation
                                    .execute(changeContext, sqlConnection, shard, runtimePermission))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
