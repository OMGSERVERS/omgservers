package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.MessageEncodingEnum;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageRequest;
import com.omgservers.dispatcher.service.room.dto.TransferRoomBinaryMessageResponse;
import com.omgservers.dispatcher.service.room.impl.operation.TransferWebSocketMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferRoomBinaryMessageMethodImpl implements TransferRoomBinaryMessageMethod {

    final TransferWebSocketMessageOperation transferWebSocketMessageOperation;

    @Override
    public Uni<TransferRoomBinaryMessageResponse> execute(final TransferRoomBinaryMessageRequest request) {
        log.trace("{}", request);

        final var dispatcherConnection = request.getDispatcherConnection();
        final var buffer = request.getBuffer();
        final var message = Base64.getEncoder().encodeToString(buffer.getBytes());

        return transferWebSocketMessageOperation.execute(dispatcherConnection, MessageEncodingEnum.B64, message)
                .map(TransferRoomBinaryMessageResponse::new);
    }
}
