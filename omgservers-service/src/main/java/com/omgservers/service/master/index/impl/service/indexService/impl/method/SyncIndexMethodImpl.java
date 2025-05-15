package com.omgservers.service.master.index.impl.service.indexService.impl.method;

import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import com.omgservers.service.master.index.impl.operation.UpsertIndexOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
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
    public Uni<SyncIndexResponse> execute(final SyncIndexRequest request) {
        log.debug("{}", request);

        final var index = request.getIndex();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        syncIndexOperation.upsertIndex(changeContext, sqlConnection, index))
                .map(ChangeContext::getResult)
                .map(SyncIndexResponse::new);
    }
}
