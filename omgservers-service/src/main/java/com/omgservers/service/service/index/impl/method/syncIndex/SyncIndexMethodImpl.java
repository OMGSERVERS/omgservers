package com.omgservers.service.service.index.impl.method.syncIndex;

import com.omgservers.service.service.index.dto.SyncIndexRequest;
import com.omgservers.service.service.index.dto.SyncIndexResponse;
import com.omgservers.service.service.index.operation.upsertIndex.UpsertIndexOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncIndexMethodImpl implements SyncIndexMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertIndexOperation syncIndexOperation;

    @Override
    public Uni<SyncIndexResponse> syncIndex(SyncIndexRequest request) {
        log.debug("Sync index, request={}", request);

        final var index = request.getIndex();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        syncIndexOperation.upsertIndex(changeContext, sqlConnection, index))
                .map(ChangeContext::getResult)
                .map(SyncIndexResponse::new);
    }
}
