package com.omgservers.service.module.system.impl.service.containerService.impl.method.syncContainer;

import com.omgservers.model.dto.system.SyncContainerRequest;
import com.omgservers.model.dto.system.SyncContainerResponse;
import com.omgservers.service.module.system.impl.operation.upsertContainer.UpsertContainerOperation;
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
class SyncContainerMethodImpl implements SyncContainerMethod {

    final UpsertContainerOperation upsertContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncContainerResponse> syncContainer(final SyncContainerRequest request) {
        log.debug("Sync container, request={}", request);

        final var container = request.getContainer();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertContainerOperation.upsertContainer(changeContext,
                                sqlConnection,
                                container))
                .map(ChangeContext::getResult)
                .map(SyncContainerResponse::new);
    }
}
