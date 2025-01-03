package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomTextMessageResponse;
import com.omgservers.dispatcher.service.room.impl.operation.TransferWebSocketMessageOperation;
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
        log.trace("{}", request);

        final var dispatcherConnection = request.getDispatcherConnection();
        final var message = request.getMessage();

        return transferWebSocketMessageOperation.execute(dispatcherConnection, MessageEncodingEnum.TXT, message)
                .map(TransferRoomTextMessageResponse::new);
    }
}
