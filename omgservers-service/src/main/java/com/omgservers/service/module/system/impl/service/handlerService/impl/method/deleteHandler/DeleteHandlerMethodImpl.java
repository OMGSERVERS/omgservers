package com.omgservers.service.module.system.impl.service.handlerService.impl.method.deleteHandler;

import com.omgservers.model.dto.system.DeleteHandlerRequest;
import com.omgservers.model.dto.system.DeleteHandlerResponse;
import com.omgservers.service.module.system.impl.operation.deleteHandler.DeleteHandlerOperation;
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
class DeleteHandlerMethodImpl implements DeleteHandlerMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteHandlerOperation deleteHandlerOperation;

    @Override
    public Uni<DeleteHandlerResponse> deleteHandler(final DeleteHandlerRequest request) {
        log.debug("Delete handler, request={}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteHandlerOperation.deleteHandler(changeContext,
                                sqlConnection,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteHandlerResponse::new);
    }
}
