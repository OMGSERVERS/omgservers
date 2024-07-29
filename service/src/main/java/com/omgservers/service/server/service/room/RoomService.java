package com.omgservers.service.server.service.room;

import com.omgservers.service.server.service.room.dto.AddConnectionRequest;
import com.omgservers.service.server.service.room.dto.CreateRoomRequest;
import com.omgservers.service.server.service.room.dto.HandleBinaryMessageRequest;
import com.omgservers.service.server.service.room.dto.HandleTextMessageRequest;
import com.omgservers.service.server.service.room.dto.RemoveConnectionRequest;
import com.omgservers.service.server.service.room.dto.RemoveRoomRequest;
import com.omgservers.service.server.service.room.dto.AddConnectionRequest;
import com.omgservers.service.server.service.room.dto.CreateRoomRequest;
import com.omgservers.service.server.service.room.dto.HandleBinaryMessageRequest;
import com.omgservers.service.server.service.room.dto.HandleTextMessageRequest;
import com.omgservers.service.server.service.room.dto.RemoveConnectionRequest;
import com.omgservers.service.server.service.room.dto.RemoveRoomRequest;
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
