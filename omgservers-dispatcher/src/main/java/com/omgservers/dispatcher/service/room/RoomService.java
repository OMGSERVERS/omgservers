package com.omgservers.dispatcher.service.room;

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
