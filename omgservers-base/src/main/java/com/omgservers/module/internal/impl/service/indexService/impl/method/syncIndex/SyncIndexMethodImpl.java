package com.omgservers.module.internal.impl.service.indexService.impl.method.syncIndex;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.internal.SyncIndexRequest;
import com.omgservers.module.internal.impl.operation.upsertIndex.UpsertIndexOperation;
import com.omgservers.module.internal.impl.operation.validateIndex.ValidateIndexOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncIndexMethodImpl implements SyncIndexMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final ValidateIndexOperation validateIndexOperation;
    final UpsertIndexOperation syncIndexOperation;

    @Override
    public Uni<Void> syncIndex(SyncIndexRequest request) {
        SyncIndexRequest.validate(request);

        final var index = request.getIndex();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        syncIndexOperation.upsertIndex(changeContext, sqlConnection, index))
                .map(ChangeContext::getResult)
                .replaceWithVoid();
    }
}
