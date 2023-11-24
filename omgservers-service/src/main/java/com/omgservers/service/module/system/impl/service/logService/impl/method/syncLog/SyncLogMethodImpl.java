package com.omgservers.service.module.system.impl.service.logService.impl.method.syncLog;

import com.omgservers.model.dto.system.SyncLogRequest;
import com.omgservers.model.dto.system.SyncLogResponse;
import com.omgservers.service.module.system.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncLogMethodImpl implements SyncLogMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertLogOperation upsertLogOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncLogResponse> syncLog(SyncLogRequest request) {
        log.debug("Sync log, request={}", request);

        final var logModel = request.getLog();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertLogOperation.upsertLog(changeContext, sqlConnection, logModel))
                .map(ChangeContext::getResult)
                .map(SyncLogResponse::new);
    }
}
