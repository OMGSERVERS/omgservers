package com.omgservers.service.module.dispatcher.impl.service.roomService.impl;

import com.omgservers.service.module.dispatcher.impl.service.roomService.RoomService;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomBinaryMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.AddPlayerConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.CreateRoomMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.RemovePlayerConnectionMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.RemoveRoomMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.TransferRoomBinaryMessageMethod;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method.TransferRoomTextMessageMethod;
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

    final TransferRoomBinaryMessageMethod transferRoomBinaryMessageMethod;
    final TransferRoomTextMessageMethod transferRoomTextMessageMethod;
    final RemovePlayerConnectionMethod removePlayerConnectionMethod;
    final AddPlayerConnectionMethod addPlayerConnectionMethod;
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
    public Uni<AddPlayerConnectionResponse> addPlayerConnection(@Valid final AddPlayerConnectionRequest request) {
        return addPlayerConnectionMethod.execute(request);
    }

    @Override
    public Uni<RemovePlayerConnectionResponse> removePlayerConnection(@Valid final RemovePlayerConnectionRequest request) {
        return removePlayerConnectionMethod.execute(request);
    }

    @Override
    public Uni<TransferRoomTextMessageResponse> transferRoomTextMessage(
            @Valid final TransferRoomTextMessageRequest request) {
        return transferRoomTextMessageMethod.execute(request);
    }

    @Override
    public Uni<TransferRoomBinaryMessageResponse> transferRoomBinaryMessage(
            @Valid final TransferRoomBinaryMessageRequest request) {
        return transferRoomBinaryMessageMethod.execute(request);
    }
}
