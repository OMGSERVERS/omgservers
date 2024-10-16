package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface TransferRoomTextMessageMethod {
    Uni<TransferRoomTextMessageResponse> execute(TransferRoomTextMessageRequest request);
}
