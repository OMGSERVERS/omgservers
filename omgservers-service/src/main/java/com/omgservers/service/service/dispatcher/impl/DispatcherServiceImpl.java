package com.omgservers.service.service.dispatcher.impl;

import com.omgservers.service.service.dispatcher.dto.AddConnectionRequest;
import com.omgservers.service.service.dispatcher.dto.CreateRoomRequest;
import com.omgservers.service.service.dispatcher.dto.CreateRoomResponse;
import com.omgservers.service.service.dispatcher.dto.HandleBinaryMessageRequest;
import com.omgservers.service.service.dispatcher.dto.HandleTextMessageRequest;
import com.omgservers.service.service.dispatcher.dto.RemoveConnectionRequest;
import com.omgservers.service.service.dispatcher.dto.RemoveRoomRequest;
import com.omgservers.service.service.dispatcher.dto.RemoveRoomResponse;
import com.omgservers.service.service.dispatcher.impl.method.addConnection.AddConnectionMethod;
import com.omgservers.service.service.dispatcher.impl.method.createRoom.CreateRoomMethod;
import com.omgservers.service.service.dispatcher.impl.method.handleBinaryMessage.HandleBinaryMessageMethod;
import com.omgservers.service.service.dispatcher.impl.method.handleTextMessage.HandleTextMessageMethod;
import com.omgservers.service.service.dispatcher.impl.method.removeConnection.RemoveConnectionMethod;
import com.omgservers.service.service.dispatcher.impl.method.removeRoom.RemoveRoomMethod;
import com.omgservers.service.service.dispatcher.DispatcherService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DispatcherServiceImpl implements DispatcherService {

    final HandleBinaryMessageMethod handleBinaryMessageMethod;
    final HandleTextMessageMethod handleTextMessageMethod;
    final RemoveConnectionMethod removeConnectionMethod;
    final AddConnectionMethod addConnectionMethod;
    final CreateRoomMethod createRoomMethod;
    final RemoveRoomMethod removeRoomMethod;

    @Override
    public Uni<CreateRoomResponse> createRoom(@Valid final CreateRoomRequest request) {
        return createRoomMethod.creteRoom(request);
    }

    @Override
    public Uni<RemoveRoomResponse> removeRoom(@Valid final RemoveRoomRequest request) {
        return removeRoomMethod.removeRoom(request);
    }

    @Override
    public Uni<Void> addConnection(@Valid final AddConnectionRequest request) {
        return addConnectionMethod.addConnection(request);
    }

    @Override
    public Uni<Void> removeConnection(@Valid final RemoveConnectionRequest request) {
        return removeConnectionMethod.removeConnection(request);
    }

    @Override
    public Uni<Void> handleTextMessage(@Valid final HandleTextMessageRequest request) {
        return handleTextMessageMethod.handleTextMessage(request);
    }

    @Override
    public Uni<Void> handleBinaryMessage(@Valid final HandleBinaryMessageRequest request) {
        return handleBinaryMessageMethod.handleBinaryMessage(request);
    }
}
