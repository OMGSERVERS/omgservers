package com.omgservers.service.server.service.event.impl;

import com.omgservers.schema.service.system.HandleEventRequest;
import com.omgservers.schema.service.system.HandleEventResponse;
import com.omgservers.schema.service.system.RelayEventsRequest;
import com.omgservers.schema.service.system.RelayEventsResponse;
import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.server.service.event.EventService;
import com.omgservers.service.server.service.event.impl.method.handleEvent.HandleEventMethod;
import com.omgservers.service.server.service.event.impl.method.relayEvents.RelayEventsMethod;
import com.omgservers.service.server.service.event.impl.method.syncEvent.SyncEventMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class EventServiceImpl implements EventService {

    final HandleEventMethod handleEventMethod;
    final RelayEventsMethod relayEventsMethod;
    final SyncEventMethod syncEventMethod;

    @Override
    public Uni<SyncEventResponse> syncEvent(@Valid final SyncEventRequest request) {
        return syncEventMethod.syncEvent(request);
    }

    @Override
    public Uni<SyncEventResponse> syncEventWithIdempotency(@Valid final SyncEventRequest request) {
        return syncEvent(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.warn("Idempotency was violated, object={}, {}", request.getEvent(), t.getMessage());
                            return Uni.createFrom().item(new SyncEventResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<RelayEventsResponse> relayEvents(@Valid final RelayEventsRequest request) {
        return relayEventsMethod.relayEvents(request);
    }

    @Override
    public Uni<HandleEventResponse> handleEvent(@Valid final HandleEventRequest request) {
        return handleEventMethod.handleEvent(request);
    }
}
