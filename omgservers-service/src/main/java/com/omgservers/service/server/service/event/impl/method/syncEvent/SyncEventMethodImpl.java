package com.omgservers.service.server.service.event.impl.method.syncEvent;

import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import com.omgservers.service.server.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.service.server.operation.changeWithContext.ChangeContext;
import com.omgservers.service.server.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncEventMethodImpl implements SyncEventMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertEventOperation upsertEventOperation;

    @Override
    public Uni<SyncEventResponse> syncEvent(final SyncEventRequest request) {
        final var event = request.getEvent();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertEventOperation.upsertEvent(changeContext, sqlConnection, event))
                .map(ChangeContext::getResult)
                .map(SyncEventResponse::new);
    }
}
