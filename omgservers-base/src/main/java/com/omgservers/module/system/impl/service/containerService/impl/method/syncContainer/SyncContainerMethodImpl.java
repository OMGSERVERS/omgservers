package com.omgservers.module.system.impl.service.containerService.impl.method.syncContainer;

import com.omgservers.dto.internal.SyncContainerRequest;
import com.omgservers.dto.internal.SyncContainerResponse;
import com.omgservers.module.system.impl.operation.upsertContainer.UpsertContainerOperation;
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
class SyncContainerMethodImpl implements SyncContainerMethod {

    final UpsertContainerOperation upsertContainerOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncContainerResponse> syncContainer(final SyncContainerRequest request) {
        final var container = request.getContainer();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertContainerOperation.upsertContainer(changeContext,
                                sqlConnection,
                                container))
                .map(ChangeContext::getResult)
                .map(SyncContainerResponse::new);
    }
}
