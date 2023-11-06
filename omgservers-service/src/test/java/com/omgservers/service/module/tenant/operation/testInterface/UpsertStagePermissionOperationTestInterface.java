package com.omgservers.service.module.tenant.operation.testInterface;

import com.omgservers.model.stagePermission.StagePermissionModel;
import com.omgservers.service.module.tenant.impl.operation.upsertStagePermission.UpsertStagePermissionOperation;
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
public class UpsertStagePermissionOperationTestInterface {
    private static final long TIMEOUT = 1L;

    final UpsertStagePermissionOperation upsertStagePermissionOperation;

    final PgPool pgPool;

    public ChangeContext<Boolean> upsertStagePermission(final int shard,
                                                        final StagePermissionModel stagePermission) {
        return Uni.createFrom().context(context -> {
                    final var changeContext = new ChangeContext<Boolean>(context);
                    return pgPool.withTransaction(sqlConnection -> upsertStagePermissionOperation
                                    .upsertStagePermission(changeContext, sqlConnection, shard, stagePermission))
                            .invoke(changeContext::setResult)
                            .replaceWith(changeContext);
                })
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
