package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferRoomBinaryMessageMethod {
    Uni<TransferRoomBinaryMessageResponse> execute(TransferRoomBinaryMessageRequest request);
}
