package com.omgservers.service.server.service.room;

import com.omgservers.service.server.service.room.dto.AddConnectionRequest;
import com.omgservers.service.server.service.room.dto.CreateRoomRequest;
import com.omgservers.service.server.service.room.dto.CreateRoomResponse;
import com.omgservers.service.server.service.room.dto.HandleBinaryMessageRequest;
import com.omgservers.service.server.service.room.dto.HandleTextMessageRequest;
import com.omgservers.service.server.service.room.dto.RemoveConnectionRequest;
import com.omgservers.service.server.service.room.dto.RemoveRoomRequest;
import com.omgservers.service.server.service.room.dto.RemoveRoomResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RoomService {

    Uni<CreateRoomResponse> createRoom(@Valid CreateRoomRequest request);

    Uni<RemoveRoomResponse> removeRoom(@Valid RemoveRoomRequest request);

    Uni<Void> addConnection(@Valid AddConnectionRequest request);

    Uni<Void> removeConnection(@Valid RemoveConnectionRequest request);

    Uni<Void> handleTextMessage(@Valid HandleTextMessageRequest request);

    Uni<Void> handleBinaryMessage(@Valid HandleBinaryMessageRequest request);
}
