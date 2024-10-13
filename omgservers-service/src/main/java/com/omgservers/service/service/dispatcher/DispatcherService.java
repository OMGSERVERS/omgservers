package com.omgservers.service.service.dispatcher;

import com.omgservers.service.service.dispatcher.dto.AddConnectionRequest;
import com.omgservers.service.service.dispatcher.dto.CreateRoomRequest;
import com.omgservers.service.service.dispatcher.dto.CreateRoomResponse;
import com.omgservers.service.service.dispatcher.dto.HandleBinaryMessageRequest;
import com.omgservers.service.service.dispatcher.dto.HandleTextMessageRequest;
import com.omgservers.service.service.dispatcher.dto.RemoveConnectionRequest;
import com.omgservers.service.service.dispatcher.dto.RemoveRoomRequest;
import com.omgservers.service.service.dispatcher.dto.RemoveRoomResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface DispatcherService {

    Uni<CreateRoomResponse> createRoom(@Valid CreateRoomRequest request);

    Uni<RemoveRoomResponse> removeRoom(@Valid RemoveRoomRequest request);

    Uni<Void> addConnection(@Valid AddConnectionRequest request);

    Uni<Void> removeConnection(@Valid RemoveConnectionRequest request);

    Uni<Void> handleTextMessage(@Valid HandleTextMessageRequest request);

    Uni<Void> handleBinaryMessage(@Valid HandleBinaryMessageRequest request);
}
