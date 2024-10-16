package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.TransferRoomTextMessageResponse;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation.TransferWebSocketMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferRoomTextMessageMethodImpl implements TransferRoomTextMessageMethod {

    final TransferWebSocketMessageOperation transferWebSocketMessageOperation;

    @Override
    public Uni<TransferRoomTextMessageResponse> execute(final TransferRoomTextMessageRequest request) {
        log.debug("Handle text message, request={}", request);

        final var dispatcherConnection = request.getDispatcherConnection();
        final var message = request.getMessage();

        return transferWebSocketMessageOperation.execute(dispatcherConnection, MessageEncodingEnum.TXT, message)
                .map(TransferRoomTextMessageResponse::new);
    }
}
