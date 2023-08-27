package com.omgservers.module.internal.impl.service.logService.impl.method.viewLogs;

import com.omgservers.dto.internal.ViewLogRequest;
import com.omgservers.dto.internal.ViewLogsResponse;
import com.omgservers.module.internal.impl.operation.selectLogs.SelectLogsOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewLogsMethodImpl implements ViewLogsMethod {

    final SelectLogsOperation selectLogsOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewLogsResponse> viewLogs(ViewLogRequest request) {
        ViewLogRequest.validate(request);

        return pgPool.withTransaction(selectLogsOperation::selectLogs)
                .map(ViewLogsResponse::new);
    }
}
