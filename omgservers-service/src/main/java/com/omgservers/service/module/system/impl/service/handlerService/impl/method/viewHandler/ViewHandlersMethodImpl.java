package com.omgservers.service.module.system.impl.service.handlerService.impl.method.viewHandler;

import com.omgservers.model.dto.system.ViewHandlersRequest;
import com.omgservers.model.dto.system.ViewHandlersResponse;
import com.omgservers.service.module.system.impl.operation.selectHandlers.SelectHandlersOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewHandlersMethodImpl implements ViewHandlersMethod {

    final SelectHandlersOperation selectHandlersOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewHandlersResponse> viewHandlers(final ViewHandlersRequest request) {
        log.debug("View handlers, request={}", request);

        return pgPool.withTransaction(selectHandlersOperation::selectHandlers)
                .map(ViewHandlersResponse::new);
    }
}
