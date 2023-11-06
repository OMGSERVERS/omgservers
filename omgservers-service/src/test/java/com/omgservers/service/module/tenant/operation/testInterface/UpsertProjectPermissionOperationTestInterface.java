package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.projectPermission.ProjectPermissionModel;
import com.omgservers.service.module.tenant.impl.operation.upsertProjectPermission.UpsertProjectPermissionOperation;
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
public class UpsertProjectPermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertProjectPermissionOperation upsertProjectPermissionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertProjectPermission(final int shard,
                                                          final ProjectPermissionModel projectPermission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertProjectPermissionOperation
                                    .upsertProjectPermission(changeContext, sqlConnection, shard, projectPermission))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
