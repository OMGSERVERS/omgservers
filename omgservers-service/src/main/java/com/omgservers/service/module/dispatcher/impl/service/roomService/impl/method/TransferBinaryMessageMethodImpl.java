package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.MessageEncodingEnum;
import com.omgservers.service.module.dispatcher.impl.service.roomService.impl.operation.TransferWebSocketMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TransferBinaryMessageMethodImpl implements TransferBinaryMessageMethod {

    final TransferWebSocketMessageOperation transferWebSocketMessageOperation;

    @Override
    public Uni<Void> execute(final HandleBinaryMessageRequest request) {
        log.debug("Transfer binary message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();
        final var message = Base64.getEncoder().encodeToString(buffer.getBytes());

        return transferWebSocketMessageOperation.execute(webSocketConnection, MessageEncodingEnum.B64, message);
    }
}
