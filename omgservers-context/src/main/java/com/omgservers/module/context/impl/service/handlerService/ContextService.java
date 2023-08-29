package com.omgservers.module.context.impl.service.handlerService;

import com.omgservers.dto.context.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddActorRuntimeCommandResponse;
import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandResponse;
import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandResponse;
import com.omgservers.dto.context.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import com.omgservers.dto.context.HandleMatchCreatedEventRequest;
import com.omgservers.dto.context.HandleMatchCreatedEventResponse;
import com.omgservers.dto.context.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedInEventResponse;
import com.omgservers.dto.context.HandlePlayerSignedUpEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedUpEventResponse;
import com.omgservers.dto.context.HandleStopRuntimeCommandRequest;
import com.omgservers.dto.context.HandleStopRuntimeCommandResponse;
import io.smallrye.mutiny.Uni;

public interface ContextService {
    Uni<HandlePlayerSignedUpEventResponse> handlePlayerSignedUpEvent(HandlePlayerSignedUpEventRequest request);

    Uni<HandlePlayerSignedInEventResponse> handlePlayerSignedInEvent(HandlePlayerSignedInEventRequest request);

    Uni<HandleMatchCreatedEventResponse> handleMatchCreatedEvent(HandleMatchCreatedEventRequest request);

    Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request);

    Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request);

    Uni<HandleAddActorRuntimeCommandResponse> handleAddActorRuntimeCommand(HandleAddActorRuntimeCommandRequest request);

    Uni<HandleDeleteActorRuntimeCommandResponse> handleDeleteActorRuntimeCommand(HandleDeleteActorRuntimeCommandRequest request);

    Uni<HandleHandleIncomingRuntimeCommandResponse> handleHandleIncomingRuntimeCommand(HandleHandleIncomingRuntimeCommandRequest request);
}
