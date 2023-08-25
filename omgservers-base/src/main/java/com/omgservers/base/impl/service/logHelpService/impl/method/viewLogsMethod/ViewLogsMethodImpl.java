package com.omgservers.base.impl.service.logHelpService.impl.method.viewLogsMethod;

import com.omgservers.base.impl.operation.selectLogsOperation.SelectLogsOperation;
import com.omgservers.base.impl.service.logHelpService.request.ViewLogsHelpRequest;
import com.omgservers.base.impl.service.logHelpService.response.ViewLogsHelpResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ViewLogsMethodImpl implements ViewLogsMethod {

    final SelectLogsOperation selectLogsOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewLogsHelpResponse> viewLogs(ViewLogsHelpRequest request) {
        ViewLogsHelpRequest.validate(request);

        return pgPool.withTransaction(selectLogsOperation::selectLogs)
                .map(ViewLogsHelpResponse::new);
    }
}
