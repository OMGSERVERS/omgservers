package com.omgservers.service.module.system.impl.service.handlerService.impl.method.getHandler;

import com.omgservers.model.dto.system.GetHandlerRequest;
import com.omgservers.model.dto.system.GetHandlerResponse;
import com.omgservers.service.module.system.impl.operation.selectHandler.SelectHandlerOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetHandlerMethodImpl implements GetHandlerMethod {

    final SelectHandlerOperation selectHandlerOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetHandlerResponse> getHandler(final GetHandlerRequest request) {
        log.trace("Get handler, request={}", request);

        final var id = request.getId();
        return pgPool.withTransaction(sqlConnection -> selectHandlerOperation
                        .selectHandler(sqlConnection, id))
                .map(GetHandlerResponse::new);
    }
}
