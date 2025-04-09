package com.omgservers.service.server.event.impl.method;

import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.server.event.operation.UpsertEventOperation;
import com.omgservers.service.server.event.dto.SyncEventRequest;
import com.omgservers.service.server.event.dto.SyncEventResponse;
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
