package com.omgservers.service.module.system.impl.service.containerService.impl.method.deleteContainer;

import com.omgservers.model.dto.system.DeleteContainerRequest;
import com.omgservers.model.dto.system.DeleteContainerResponse;
import com.omgservers.service.module.system.impl.operation.deleteContainer.DeleteContainerOperation;
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
