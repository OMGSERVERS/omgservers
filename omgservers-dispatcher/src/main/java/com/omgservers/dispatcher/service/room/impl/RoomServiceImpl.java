package com.omgservers.dispatcher.service.room.impl;

import com.omgservers.dispatcher.service.room.RoomService;
import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionResponse;
import com.omgservers.dispatcher.service.room.dto.CreateRoomRequest;
import com.omgservers.dispatcher.service.room.dto.CreateRoomResponse;
import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionResponse;
import com.omgservers.dispatcher.service.room.dto.RemoveRoomRequest;
import com.omgservers.dispatcher.service.room.dto.RemoveRoomResponse;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageResponse;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageResponse;
import com.omgservers.dispatcher.service.room.impl.method.AddPlayerConnectionMethod;
import com.omgservers.dispatcher.service.room.impl.method.CreateRoomMethod;
import com.omgservers.dispatcher.service.room.impl.method.RemovePlayerConnectionMethod;
import com.omgservers.dispatcher.service.room.impl.method.RemoveRoomMethod;
import com.omgservers.dispatcher.service.room.impl.method.TransferRoomBinaryMessageMethod;
import com.omgservers.dispatcher.service.room.impl.method.TransferRoomTextMessageMethod;
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
    public Uni<RemovePlayerConnectionResponse> removePlayerConnection(
            @Valid final RemovePlayerConnectionRequest request) {
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
