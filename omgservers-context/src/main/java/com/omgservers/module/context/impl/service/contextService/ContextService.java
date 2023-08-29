package com.omgservers.module.context.impl.service.contextService;

import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandResponse;
import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandResponse;
import com.omgservers.dto.context.HandleHandleEventRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleEventRuntimeCommandResponse;
import com.omgservers.dto.context.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
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

    Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request);

    Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request);

    Uni<HandleAddPlayerRuntimeCommandResponse> handleAddPlayerRuntimeCommand(HandleAddPlayerRuntimeCommandRequest request);

    Uni<HandleDeletePlayerRuntimeCommandResponse> handleDeletePlayerRuntimeCommand(HandleDeletePlayerRuntimeCommandRequest request);

    Uni<HandleHandleEventRuntimeCommandResponse> handleHandleEventRuntimeCommand(HandleHandleEventRuntimeCommandRequest request);
}
