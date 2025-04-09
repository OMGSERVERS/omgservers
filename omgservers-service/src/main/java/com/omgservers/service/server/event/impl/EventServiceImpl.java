package com.omgservers.service.server.event.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.server.event.EventService;
import com.omgservers.service.server.event.dto.*;
import com.omgservers.service.server.event.dto.HandleEventsResponse;
import com.omgservers.service.server.event.impl.method.HandleEventMethod;
import com.omgservers.service.server.event.impl.method.HandleEventsMethod;
import com.omgservers.service.server.event.impl.method.SyncEventMethod;
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

    final HandleEventsMethod handleEventsMethod;
    final HandleEventMethod handleEventMethod;
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
                            log.debug("Idempotency was violated, object={}, {}", request.getEvent(), t.getMessage());
                            return Uni.createFrom().item(new SyncEventResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<HandleEventsResponse> handleEvents(@Valid final HandleEventsRequest request) {
        return handleEventsMethod.execute(request);
    }

    @Override
    public Uni<HandleEventResponse> handleEvent(@Valid final HandleEventRequest request) {
        return handleEventMethod.handleEvent(request);
    }
}
