package com.omgservers.service.service.dispatcher.impl.method.handleBinaryMessage;

import com.omgservers.service.service.dispatcher.dto.HandleBinaryMessageRequest;
import com.omgservers.service.service.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.service.service.dispatcher.impl.operation.HandleWebSocketMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleBinaryMessageMethodImpl implements HandleBinaryMessageMethod {

    final HandleWebSocketMessageOperation handleWebSocketMessageOperation;

    @Override
    public Uni<Void> handleBinaryMessage(final HandleBinaryMessageRequest request) {
        log.debug("Handle binary message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var buffer = request.getBuffer();
        final var message = Base64.getEncoder().encodeToString(buffer.getBytes());

        return handleWebSocketMessageOperation.execute(webSocketConnection, MessageEncodingEnum.B64, message);
    }
}
