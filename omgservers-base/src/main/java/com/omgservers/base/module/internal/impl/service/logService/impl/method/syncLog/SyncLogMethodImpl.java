package com.omgservers.base.module.internal.impl.service.logService.impl.method.syncLog;

import com.omgservers.base.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.dto.internalModule.SyncLogRequest;
import com.omgservers.dto.internalModule.SyncLogResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SyncLogMethodImpl implements SyncLogMethod {

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
