package com.omgservers.module.system.impl.service.eventService.impl.method.syncEvent;

import com.omgservers.model.dto.internal.SyncEventRequest;
import com.omgservers.model.dto.internal.SyncEventResponse;
import com.omgservers.module.system.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
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
