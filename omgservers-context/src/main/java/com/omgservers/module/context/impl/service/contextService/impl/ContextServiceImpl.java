package com.omgservers.module.context.impl.service.contextService.impl;

import com.omgservers.dto.context.CreateLuaInstanceForPlayerEventsRequest;
import com.omgservers.dto.context.CreateLuaInstanceForPlayerEventsResponse;
import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsRequest;
import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsResponse;
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
import com.omgservers.dto.context.HandleUpdateRuntimeCommandRequest;
import com.omgservers.dto.context.HandleUpdateRuntimeCommandResponse;
import com.omgservers.module.context.impl.service.contextService.ContextService;
import com.omgservers.module.context.impl.service.contextService.impl.method.createLuaInstanceForPlayerEvents.CreateLuaInstanceForPlayerEventsMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.createLuaInstanceForRuntimeEvents.CreateLuaInstanceForRuntimeEventsMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handleAddPlayerRuntimeCommand.HandleAddPlayerRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handleDeletePlayerRuntimeCommand.HandleDeletePlayerRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handleHandleEventRuntimeCommand.HandleHandleEventRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handleInitRuntimeCommand.HandleInitRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedInEvent.HandlePlayerSignedInEventMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedUpEvent.HandlePlayerSignedUpEventMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handleStopRuntimeCommand.HandleStopRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.contextService.impl.method.handleUpdateRuntimeCommand.HandleUpdateRuntimeCommandMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ContextServiceImpl implements ContextService {

    final CreateLuaInstanceForPlayerEventsMethod createLuaInstanceForPlayerEventsMethod;
    final HandlePlayerSignedUpEventMethod handlePlayerSignedUpEventHelpMethod;
    final HandlePlayerSignedInEventMethod handlePlayerSignedInEventHelpMethod;

    final CreateLuaInstanceForRuntimeEventsMethod createLuaInstanceForRuntimeEventsMethod;
    final HandleDeletePlayerRuntimeCommandMethod handleDeletePlayerRuntimeCommandMethod;
    final HandleHandleEventRuntimeCommandMethod handleHandleEventRuntimeCommandMethod;
    final HandleAddPlayerRuntimeCommandMethod handleAddPlayerRuntimeCommandMethod;
    final HandleUpdateRuntimeCommandMethod handleUpdateRuntimeCommandMethod;
    final HandleInitRuntimeCommandMethod handleInitRuntimeCommandMethod;
    final HandleStopRuntimeCommandMethod handleStopRuntimeCommandMethod;

    @Override
    public Uni<CreateLuaInstanceForPlayerEventsResponse> createLuaInstanceForPlayerEvents(final CreateLuaInstanceForPlayerEventsRequest request) {
        return createLuaInstanceForPlayerEventsMethod.createLuaInstanceForPlayerEvents(request);
    }

    @Override
    public Uni<HandlePlayerSignedUpEventResponse> handlePlayerSignedUpEvent(final HandlePlayerSignedUpEventRequest request) {
        return handlePlayerSignedUpEventHelpMethod.handleLuaPlayerSignedUpEvent(request);
    }

    @Override
    public Uni<HandlePlayerSignedInEventResponse> handlePlayerSignedInEvent(final HandlePlayerSignedInEventRequest request) {
        return handlePlayerSignedInEventHelpMethod.handleLuaPlayerSignedInEvent(request);
    }

    @Override
    public Uni<CreateLuaInstanceForRuntimeEventsResponse> createLuaInstanceForRuntimeEvents(final CreateLuaInstanceForRuntimeEventsRequest request) {
        return createLuaInstanceForRuntimeEventsMethod.createLuaInstanceForRuntimeEvents(request);
    }

    @Override
    public Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(final HandleInitRuntimeCommandRequest request) {
        return handleInitRuntimeCommandMethod.handleInitRuntimeCommand(request);
    }

    @Override
    public Uni<HandleUpdateRuntimeCommandResponse> handleUpdateRuntimeCommand(final HandleUpdateRuntimeCommandRequest request) {
        return handleUpdateRuntimeCommandMethod.handleUpdateRuntimeCommand(request);
    }

    @Override
    public Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(final HandleStopRuntimeCommandRequest request) {
        return handleStopRuntimeCommandMethod.handleStopRuntimeCommand(request);
    }

    @Override
    public Uni<HandleAddPlayerRuntimeCommandResponse> handleAddPlayerRuntimeCommand(final HandleAddPlayerRuntimeCommandRequest request) {
        return handleAddPlayerRuntimeCommandMethod.handleAddPlayerRuntimeCommand(request);
    }

    @Override
    public Uni<HandleDeletePlayerRuntimeCommandResponse> handleDeletePlayerRuntimeCommand(final HandleDeletePlayerRuntimeCommandRequest request) {
        return handleDeletePlayerRuntimeCommandMethod.handleDeletePlayerRuntimeCommand(request);
    }

    @Override
    public Uni<HandleHandleEventRuntimeCommandResponse> handleHandleEventRuntimeCommand(final HandleHandleEventRuntimeCommandRequest request) {
        return handleHandleEventRuntimeCommandMethod.handleHandleEventRuntimeCommand(request);
    }
}
