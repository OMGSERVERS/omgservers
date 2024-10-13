package com.omgservers.service.service.dispatcher.impl.method.handleTextMessage;

import com.omgservers.service.service.dispatcher.dto.HandleTextMessageRequest;
import com.omgservers.service.service.dispatcher.dto.MessageEncodingEnum;
import com.omgservers.service.service.dispatcher.impl.operation.HandleWebSocketMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleTextMessageMethodImpl implements HandleTextMessageMethod {

    final HandleWebSocketMessageOperation handleWebSocketMessageOperation;

    @Override
    public Uni<Void> handleTextMessage(final HandleTextMessageRequest request) {
        log.debug("Handle text message, request={}", request);

        final var webSocketConnection = request.getWebSocketConnection();
        final var message = request.getMessage();

        return handleWebSocketMessageOperation.execute(webSocketConnection, MessageEncodingEnum.TXT, message);
    }
}
