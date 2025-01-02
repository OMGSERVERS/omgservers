package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferRoomTextMessageMethod {
    Uni<TransferRoomTextMessageResponse> execute(TransferRoomTextMessageRequest request);
}
