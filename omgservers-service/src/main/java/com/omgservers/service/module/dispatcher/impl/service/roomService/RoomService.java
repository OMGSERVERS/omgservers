package com.omgservers.service.module.dispatcher.impl.service.roomService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RoomService {

    Uni<CreateRoomResponse> createRoom(@Valid CreateRoomRequest request);

    Uni<RemoveRoomResponse> removeRoom(@Valid RemoveRoomRequest request);

    Uni<AddPlayerConnectionResponse> addPlayerConnection(@Valid AddPlayerConnectionRequest request);

    Uni<RemovePlayerConnectionResponse> removePlayerConnection(@Valid RemovePlayerConnectionRequest request);

    Uni<TransferRoomTextMessageResponse> transferRoomTextMessage(@Valid TransferRoomTextMessageRequest request);

    Uni<TransferRoomBinaryMessageResponse> transferRoomBinaryMessage(@Valid TransferRoomBinaryMessageRequest request);
}
