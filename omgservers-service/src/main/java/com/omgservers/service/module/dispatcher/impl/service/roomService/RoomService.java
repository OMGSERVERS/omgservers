package com.omgservers.service.module.dispatcher.impl.service.roomService;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomResponse;
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
