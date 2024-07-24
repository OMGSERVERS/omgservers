package com.omgservers.service.service.room;

import com.omgservers.service.service.room.dto.AddConnectionRequest;
import com.omgservers.service.service.room.dto.CreateRoomRequest;
import com.omgservers.service.service.room.dto.HandleBinaryMessageRequest;
import com.omgservers.service.service.room.dto.HandleTextMessageRequest;
import com.omgservers.service.service.room.dto.RemoveConnectionRequest;
import com.omgservers.service.service.room.dto.RemoveRoomRequest;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RoomService {

    Uni<Void> createRoom(@Valid CreateRoomRequest request);

    Uni<Void> removeRoom(@Valid RemoveRoomRequest request);

    Uni<Void> addConnection(@Valid AddConnectionRequest request);

    Uni<Void> removeConnection(@Valid RemoveConnectionRequest request);

    Uni<Void> handleTextMessage(@Valid HandleTextMessageRequest request);

    Uni<Void> handleBinaryMessage(@Valid HandleBinaryMessageRequest request);
}
