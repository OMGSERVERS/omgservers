package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferRoomBinaryMessageMethod {
    Uni<TransferRoomBinaryMessageResponse> execute(TransferRoomBinaryMessageRequest request);
}
