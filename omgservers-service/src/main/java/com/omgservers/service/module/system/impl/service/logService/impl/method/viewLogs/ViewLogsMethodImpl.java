package com.omgservers.service.module.system.impl.service.logService.impl.method.viewLogs;

import com.omgservers.model.dto.system.ViewLogRequest;
import com.omgservers.model.dto.system.ViewLogsResponse;
import com.omgservers.service.module.system.impl.operation.selectAllLogs.SelectAllLogsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewLogsMethodImpl implements ViewLogsMethod {

    final SelectAllLogsOperation selectAllLogsOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewLogsResponse> viewLogs(ViewLogRequest request) {
        return pgPool.withTransaction(selectAllLogsOperation::selectAllLogs)
                .map(ViewLogsResponse::new);
    }
}
