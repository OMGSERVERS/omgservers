package com.omgservers.module.internal.impl.service.logService.impl.method.syncLog;

import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.dto.internal.SyncLogResponse;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncLogMethodImpl implements SyncLogMethod {

    final UpsertLogOperation upsertLogOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncLogResponse> syncLog(SyncLogRequest request) {
        SyncLogRequest.validate(request);
        final var logModel = request.getLog();
        return pgPool.withTransaction(sqlConnection -> upsertLogOperation
                        .upsertLog(sqlConnection, logModel))
                .map(SyncLogResponse::new);
    }
}
