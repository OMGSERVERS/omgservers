package com.omgservers.module.context.impl.service.handlerService;

import com.omgservers.dto.handler.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleMatchCreatedEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedUpEventRequest;
import com.omgservers.dto.handler.HandleStopRuntimeCommandRequest;
import io.smallrye.mutiny.Uni;

public interface ContextService {
    Uni<Void> handlePlayerSignedUpEvent(HandlePlayerSignedUpEventRequest request);

    Uni<Void> handlePlayerSignedInEvent(HandlePlayerSignedInEventRequest request);

    Uni<Void> handleMatchCreatedEvent(HandleMatchCreatedEventRequest request);

    Uni<Void> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request);

    Uni<Void> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request);

    Uni<Void> handleAddActorRuntimeCommand(HandleAddActorRuntimeCommandRequest request);

    Uni<Void> handleDeleteActorRuntimeCommand(HandleDeleteActorRuntimeCommandRequest request);

    Uni<Void> handleHandleIncomingRuntimeCommand(HandleHandleIncomingRuntimeCommandRequest request);
}
