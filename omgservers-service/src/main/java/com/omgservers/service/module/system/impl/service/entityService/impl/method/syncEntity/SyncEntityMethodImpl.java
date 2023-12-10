package com.omgservers.service.module.system.impl.service.entityService.impl.method.syncEntity;

import com.omgservers.model.dto.system.SyncEntityRequest;
import com.omgservers.model.dto.system.SyncEntityResponse;
import com.omgservers.service.module.system.impl.operation.upsertEntity.UpsertEntityOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncEntityMethodImpl implements SyncEntityMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertEntityOperation upsertEntityOperation;

    @Override
    public Uni<SyncEntityResponse> syncEntity(final SyncEntityRequest request) {
        log.debug("Sync entity, request={}", request);

        final var entity = request.getEntity();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertEntityOperation.upsertEntity(changeContext, sqlConnection, entity))
                .map(ChangeContext::getResult)
                .map(SyncEntityResponse::new);
    }
}
