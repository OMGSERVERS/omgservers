package com.omgservers.application.module.internalModule.impl.service.logHelpService.impl.method.syncLogMethod;

import com.omgservers.application.operation.upsertLogOperation.UpsertLogOperation;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.request.SyncLogHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.logHelpService.response.SyncLogHelpResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class SyncLogMethodImpl implements SyncLogMethod {

    final CheckShardOperation checkShardOperation;
    final UpsertLogOperation upsertLogOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncLogHelpResponse> syncLog(SyncLogHelpRequest request) {
        SyncLogHelpRequest.validate(request);
        final var logModel = request.getLog();
        return pgPool.withTransaction(sqlConnection -> upsertLogOperation
                        .upsertLog(sqlConnection, logModel))
                .map(SyncLogHelpResponse::new);
    }
}
