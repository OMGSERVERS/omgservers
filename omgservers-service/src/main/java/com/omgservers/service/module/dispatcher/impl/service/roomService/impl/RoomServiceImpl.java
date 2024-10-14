package com.omgservers.service.module.dispatcher.impl.service.roomService.impl;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.AddConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.CreateRoomMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.TransferBinaryMessageMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.TransferTextMessageMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.RemoveConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.RemoveRoomMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.RoomService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RoomServiceImpl implements RoomService {

    final TransferBinaryMessageMethod transferBinaryMessageMethod;
    final TransferTextMessageMethod transferTextMessageMethod;
    final RemoveConnectionMethod removeConnectionMethod;
    final AddConnectionMethod addConnectionMethod;
    final CreateRoomMethod createRoomMethod;
    final RemoveRoomMethod removeRoomMethod;

    @Override
    public Uni<CreateRoomResponse> createRoom(@Valid final CreateRoomRequest request) {
        return createRoomMethod.execute(request);
    }

    @Override
    public Uni<RemoveRoomResponse> removeRoom(@Valid final RemoveRoomRequest request) {
        return removeRoomMethod.execute(request);
    }

    @Override
    public Uni<Void> addConnection(@Valid final AddConnectionRequest request) {
        return addConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> removeConnection(@Valid final RemoveConnectionRequest request) {
        return removeConnectionMethod.execute(request);
    }

    @Override
    public Uni<Void> handleTextMessage(@Valid final HandleTextMessageRequest request) {
        return transferTextMessageMethod.execute(request);
    }

    @Override
    public Uni<Void> handleBinaryMessage(@Valid final HandleBinaryMessageRequest request) {
        return transferBinaryMessageMethod.execute(request);
    }
}
