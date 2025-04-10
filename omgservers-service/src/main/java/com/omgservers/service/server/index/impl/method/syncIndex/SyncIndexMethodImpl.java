package com.omgservers.service.server.index.impl.method.syncIndex;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.server.index.dto.SyncIndexRequest;
import com.omgservers.service.server.index.dto.SyncIndexResponse;
import com.omgservers.service.server.index.operation.UpsertIndexOperation;
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
        log.trace("{}", request);

        final var index = request.getIndex();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        syncIndexOperation.upsertIndex(changeContext, sqlConnection, index))
                .map(ChangeContext::getResult)
                .map(SyncIndexResponse::new);
    }
}
