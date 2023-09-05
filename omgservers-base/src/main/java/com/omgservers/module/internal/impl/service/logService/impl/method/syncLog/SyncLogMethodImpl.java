package com.omgservers.module.internal.impl.service.logService.impl.method.syncLog;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.internal.SyncLogRequest;
import com.omgservers.dto.internal.SyncLogResponse;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
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
        SyncLogRequest.validate(request);

        final var logModel = request.getLog();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertLogOperation.upsertLog(changeContext, sqlConnection, logModel))
                .map(ChangeContext::getResult)
                .map(SyncLogResponse::new);
    }
}
