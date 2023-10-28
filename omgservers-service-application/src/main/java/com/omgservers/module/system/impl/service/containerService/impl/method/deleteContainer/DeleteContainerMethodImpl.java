package com.omgservers.module.system.impl.service.containerService.impl.method.deleteContainer;

import com.omgservers.model.dto.internal.DeleteContainerRequest;
import com.omgservers.model.dto.internal.DeleteContainerResponse;
import com.omgservers.module.system.impl.operation.deleteContainer.DeleteContainerOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteContainerMethodImpl implements DeleteContainerMethod {

    final DeleteContainerOperation deleteContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteContainerResponse> deleteContainer(final DeleteContainerRequest request) {
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteContainerOperation.deleteContainer(changeContext,
                                sqlConnection,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteContainerResponse::new);
    }
}