package com.omgservers.service.module.system.impl.service.handlerService.impl.method.syncHandler;

import com.omgservers.model.dto.system.SyncHandlerRequest;
import com.omgservers.model.dto.system.SyncHandlerResponse;
import com.omgservers.service.module.system.impl.operation.upsertHandler.UpsertHandlerOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SyncHandlerMethodImpl implements SyncHandlerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertHandlerOperation upsertHandlerOperation;

    @Override
    public Uni<SyncHandlerResponse> syncHandler(final SyncHandlerRequest request) {
        log.debug("Sync handler, request={}", request);

        final var handler = request.getHandler();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertHandlerOperation.upsertHandler(changeContext,
                                sqlConnection,
                                handler))
                .map(ChangeContext::getResult)
                .map(SyncHandlerResponse::new);
    }
}
